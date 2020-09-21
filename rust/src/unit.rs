use std::fmt;

pub enum Unit {
    QUANTITY,
    KILOGRAM,
    LITER,
}

impl fmt::Display for Unit {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match *self {
            Unit::QUANTITY => write!(f, "x"),
            Unit::KILOGRAM => write!(f, "kg"),
            Unit::LITER => write!(f, "l"),
        }
    }
}