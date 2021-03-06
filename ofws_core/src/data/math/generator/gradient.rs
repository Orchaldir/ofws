use crate::data::math::distance::abs_diff;
use crate::data::math::interpolation::lerp;
use serde::{Deserialize, Serialize};

#[derive(new, Debug, PartialEq, Eq, Clone, Copy, Serialize, Deserialize)]
pub struct Gradient {
    value_start: u8,
    value_end: u8,
    start: u32,
    length: u32,
}

impl Gradient {
    /// Generates the gradient.
    pub fn generate(&self, input: u32) -> u8 {
        if input <= self.start {
            return self.value_start;
        }
        let distance = (input - self.start) as f32;
        let factor = distance / self.length as f32;
        lerp(self.value_start, self.value_end, factor)
    }

    /// Generates the absolute gradient.
    pub fn generate_absolute(&self, input: u32) -> u8 {
        let distance = abs_diff(self.start, input) as f32;
        let factor = distance / self.length as f32;
        lerp(self.value_start, self.value_end, factor)
    }
}
