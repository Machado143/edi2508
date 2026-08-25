# Implementation Plan - Fix Zero Balance Display

The balance is displaying as 0 because the `formatCurrency` function expects a string of digits representing cents (e.g., "100" for R$ 1,00), but it is being passed a string representation of a `Double` with a decimal point (e.g., "2474.99"). This causes the numeric parsing to fail and default to 0.

## User Review Required

> [!NOTE]
> I will switch to using the `currencyBr` function (which accepts a `Double`) in the `SummarySection` to ensure correct formatting of the calculated balance and totals.

## Proposed Changes

### UI Layer

#### [MODIFY] [SummarySection.kt](file:///C:/Users/user/Downloads/FinanceApp/FinanceApp/app/src/main/java/com/example/financeapp/ui/screens/home/SummarySection.kt)
- Import `currencyBr` from `com.example.financeapp.utils`.
- Replace `formatCurrency(balence.toString())` with `currencyBr(balance)`.
- Use `currencyBr` for `totalIncome` and `totalExpense` as well.
- Fix typo `balence` -> `balance`.
- Fix typo in labels: Change the second "Entradas" to "Saídas".

#### [MODIFY] [HomeViewModel.kt](file:///C:/Users/user/Downloads/FinanceApp/FinanceApp/app/src/main/java/com/example/financeapp/ui/screens/home/HomeViewModel.kt)
- Rename constructor parameter `insertTractionsUseCase` to `insertTransactionsUseCase`.
- Update its usage in the `addTransaction` method.

## Verification Plan

### Manual Verification
- Run the app and verify that the "Saldo", "Entradas", and "Saídas" values are correctly displayed in the Summary section.
- Add a new transaction and verify that the balance updates correctly.
