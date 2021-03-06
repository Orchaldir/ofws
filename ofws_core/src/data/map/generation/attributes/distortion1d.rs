use crate::data::map::attribute::Attribute;
use crate::data::map::generation::step::{get_attribute_id, GenerationStepError};
use crate::data::map::Map2d;
use crate::data::math::generator::generator1d::{Generator1d, Generator1dData};
use serde::{Deserialize, Serialize};
use std::convert::TryInto;

/// Shifts each column or row of an [`Attribute`] based on a [`Generator1d`].
#[derive(new)]
pub struct Distortion1d {
    attribute_id: usize,
    generator: Generator1d,
}

impl Distortion1d {
    fn distort_row(&self, y: u32, shift: u8, attribute: &Attribute, values: &mut Vec<u8>) {
        let start = attribute.get_size().to_index_risky(0, y);
        let start_value = attribute.get(start);

        for _x in 0..shift {
            values.push(start_value);
        }

        let width = attribute.get_size().width().saturating_sub(shift as u32) as usize;

        for x in 0..width {
            values.push(attribute.get(start + x));
        }
    }

    fn distort_map_along_x(&self, map: &Map2d) -> Vec<u8> {
        let length = map.size.get_area();
        let attribute = map.get_attribute(self.attribute_id);
        let mut values = Vec::with_capacity(length);

        for y in 0..map.size.height() {
            let shift = self.generator.generate(y);
            self.distort_row(y, shift, attribute, &mut values);
        }

        values
    }

    /// Shifts each each row along the x-axis based on a [`Generator1d`].
    ///
    /// ```
    ///# use ofws_core::data::map::Map2d;
    ///# use ofws_core::data::map::generation::attributes::distortion1d::Distortion1d;
    ///# use ofws_core::data::math::generator::generator1d::Generator1d::InputAsOutput;
    ///# use ofws_core::data::math::size2d::Size2d;
    /// let size = Size2d::new(3, 3);
    /// let mut map = Map2d::new(size);
    /// let values = vec![1, 2, 3, 4, 5, 6, 7, 8, 9];
    /// let attribute_id = map.create_attribute_from("test", values).unwrap();
    /// let step = Distortion1d::new(attribute_id, InputAsOutput);
    ///
    /// step.distort_along_x(&mut map);
    ///
    /// let attribute = map.get_attribute(attribute_id);
    /// assert_eq!(attribute.get_all(), &vec![1u8, 2, 3, 4, 4, 5, 7, 7, 7]);
    /// ```
    pub fn distort_along_x(&self, map: &mut Map2d) {
        info!(
            "Distort attribute '{}' of map '{}' along the x-axis.",
            map.get_attribute(self.attribute_id).get_name(),
            map.get_name()
        );

        let values = self.distort_map_along_x(map);
        let attribute = map.get_attribute_mut(self.attribute_id);

        attribute.replace_all(values);
    }

    fn distort_column(&self, x: u32, shift: u8, attribute: &Attribute, values: &mut Vec<u8>) {
        let start = attribute.get_size().to_index_risky(x, 0);
        let start_value = attribute.get(start);
        let mut index = start;
        let width = attribute.get_size().width() as usize;

        for _y in 0..shift {
            values[index] = start_value;
            index += width;
        }

        let remaining_height = attribute.get_size().height().saturating_sub(shift as u32);
        let mut distorted_index = start;

        for _y in 0..remaining_height {
            values[index] = attribute.get(distorted_index);
            index += width;
            distorted_index += width;
        }
    }

    fn distort_map_along_y(&self, map: &Map2d) -> Vec<u8> {
        let length = map.size.get_area();
        let attribute = map.get_attribute(self.attribute_id);
        let mut values = vec![0; length];

        for x in 0..map.size.width() {
            let shift = self.generator.generate(x);
            self.distort_column(x, shift, attribute, &mut values);
        }

        values
    }

    /// Shifts each each column along the y-axis based on a [`Generator1d`].
    ///
    /// ```
    ///# use ofws_core::data::map::Map2d;
    ///# use ofws_core::data::map::generation::attributes::distortion1d::Distortion1d;
    ///# use ofws_core::data::math::generator::generator1d::Generator1d::InputAsOutput;
    ///# use ofws_core::data::math::size2d::Size2d;
    /// let size = Size2d::new(3, 3);
    /// let mut map = Map2d::new(size);
    /// let values = vec![1, 2, 3, 4, 5, 6, 7, 8, 9];
    /// let attribute_id = map.create_attribute_from("test", values).unwrap();
    /// let step = Distortion1d::new(attribute_id, InputAsOutput);
    ///
    /// step.distort_along_y(&mut map);
    ///
    /// let attribute = map.get_attribute(attribute_id);
    /// assert_eq!(attribute.get_all(), &vec![1u8, 2, 3, 4, 2, 3, 7, 5, 3]);
    /// ```
    pub fn distort_along_y(&self, map: &mut Map2d) {
        info!(
            "Distort attribute '{}' of map '{}' along the y-axis.",
            map.get_attribute(self.attribute_id).get_name(),
            map.get_name()
        );

        let values = self.distort_map_along_y(map);
        let attribute = map.get_attribute_mut(self.attribute_id);

        attribute.replace_all(values);
    }
}

/// For serializing, deserializing & validating [`Distortion1d`].
///
///```
///# use ofws_core::data::map::generation::attributes::distortion1d::{Distortion1d, Distortion1dData};
///# use ofws_core::data::math::generator::generator1d::Generator1dData::InputAsOutput;
///# use ofws_core::data::math::size2d::Size2d;
///# use std::convert::TryInto;
/// let data = Distortion1dData::new("test".to_string(), InputAsOutput);
/// let attributes = vec!["test".to_string()];
/// let step: Distortion1d = data.clone().try_convert(&attributes).unwrap();
/// let result: Distortion1dData = step.convert(&attributes);
/// assert_eq!(data, result)
///```
#[derive(new, Debug, PartialEq, Eq, Clone, Serialize, Deserialize)]
pub struct Distortion1dData {
    attribute: String,
    generator: Generator1dData,
}

impl Distortion1dData {
    pub fn try_convert(self, attributes: &[String]) -> Result<Distortion1d, GenerationStepError> {
        let id = get_attribute_id(&self.attribute, attributes)?;
        let generator: Generator1d = self.generator.try_into()?;
        Ok(Distortion1d::new(id, generator))
    }
}

impl Distortion1d {
    pub fn convert(&self, attributes: &[String]) -> Distortion1dData {
        let attribute = attributes[self.attribute_id].clone();
        Distortion1dData::new(attribute, (&self.generator).into())
    }
}
