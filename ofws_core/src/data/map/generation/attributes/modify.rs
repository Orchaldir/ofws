use crate::data::map::generation::step::{get_attribute_id, GenerationStepError};
use crate::data::map::Map2d;
use serde::{Deserialize, Serialize};

/// Modifies one [`Attribute`] with another transformed one.
#[derive(new, Debug, Clone)]
pub struct ModifyWithAttribute {
    source_id: usize,
    target_id: usize,
    factor: f32,
    minimum: u8,
}

impl ModifyWithAttribute {
    fn calculate_value(&self, source: u8, target: u8) -> u8 {
        (target as f32 + (source.max(self.minimum) - self.minimum) as f32 * self.factor) as u8
    }

    fn calculate_values(&self, map: &mut Map2d) -> Vec<u8> {
        let length = map.size.get_area();
        let source_attribute = map.get_attribute(self.source_id);
        let target_attribute = map.get_attribute(self.target_id);
        let mut values = Vec::with_capacity(length);

        for index in 0..length {
            let source = source_attribute.get(index);
            let target = target_attribute.get(index);
            values.push(self.calculate_value(source, target));
        }

        values
    }

    // Runs the step.
    pub fn run(&self, map: &mut Map2d) {
        info!(
            "{} attribute '{}' with attribute '{}' of map '{}'",
            if self.factor < 0.0 {
                "Decrease"
            } else {
                "Increase"
            },
            map.get_attribute(self.target_id).get_name(),
            map.get_attribute(self.source_id).get_name(),
            map.get_name()
        );

        let values = self.calculate_values(map);
        let attribute = map.get_attribute_mut(self.target_id);

        attribute.replace_all(values);
    }
}

/// For serializing, deserializing & validating [`ModifyWithAttribute`].
#[derive(new, Debug, Eq, PartialEq, Clone, Serialize, Deserialize)]
pub struct ModifyWithAttributeData {
    source: String,
    target: String,
    percentage: i32,
    minimum: u8,
}

impl ModifyWithAttributeData {
    pub fn try_convert(
        self,
        attributes: &[String],
    ) -> Result<ModifyWithAttribute, GenerationStepError> {
        let source_id = get_attribute_id(&self.source, attributes)?;
        let target_id = get_attribute_id(&self.target, attributes)?;
        Ok(ModifyWithAttribute::new(
            source_id,
            target_id,
            self.percentage as f32 / 100.0,
            self.minimum,
        ))
    }
}

impl ModifyWithAttribute {
    pub fn convert(&self, attributes: &[String]) -> ModifyWithAttributeData {
        let source = attributes[self.source_id].clone();
        let target = attributes[self.target_id].clone();
        ModifyWithAttributeData::new(source, target, (self.factor * 100.0) as i32, self.minimum)
    }
}
