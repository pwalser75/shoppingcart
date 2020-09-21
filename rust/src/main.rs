use crate::currencyamount::CurrencyAmount;

mod unit;
mod currencyamount;

fn main() {
    let u = unit::Unit::LITER;

    let price = CurrencyAmount {
        currency: String::from("CHF"),
        amount: 12.34,
    };

    println!("Hello, world! {}", u);
    println!("Price is {}", price);
}