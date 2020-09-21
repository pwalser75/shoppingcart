use std::fmt;

pub struct CurrencyAmount {
    pub(crate) currency: String,
    pub(crate) amount: f64,
}

impl fmt::Display for CurrencyAmount {
    fn fmt(&self, f: &mut std::fmt::Formatter) -> fmt::Result {
        write!(f, "{} {}", self.currency, self.amount)
    }
}