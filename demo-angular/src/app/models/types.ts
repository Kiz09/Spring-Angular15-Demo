export const CurrencyList = ['EUR', 'GBP', 'USD', 'CAD'] as const;

export type Currencies = typeof CurrencyList[number];
