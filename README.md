# **Code Submission**

Welcome to the Daily Cash Flow project!

---

## **Getting Started**

To pass the certification exam, you must complete all the tasks below:

1. Define a local database table and DAO (Data Access Object) based on the schema in  
   `app/schemas/CashFlowSchema.json` and show the app’s initial data using the provided `cashflow.db` file.

2. Get and display the user’s **All Summary data** (Income, Expense, Balance) using `AllSummarySection(CustomView)`.
    - **Note:** The data to be displayed here is the current sum of Income and Expense records along with the Balance (Income - Expense).

3. Initiate RecyclerView with `ExpensesSummaryByCategoryAdapter` on the **Home Screen**:
    - Implement navigation to the **List Expenses By Category Screen** when an item is clicked.

4. Initiate RecyclerView with `ListCashFlowAdapter` on the **List Expenses By Category Screen**:
    - Implement delete expense functionality when an item in the list is swiped.

5. Initiate RecyclerView with `ListCashFlowAdapter` on the **All Cash Flows Screen**:
    - Implement filter functionality in order to displayed data based on the chosen filter option.

6. Create `AddCashFlowActivity` and its layout (`activity_add_cashflow.xml`) to add new cashflows. *(See: Add Cash Flow Screen specification)*

7. On **AddCashFlowActivity**:
    - Display a notification using `WorkManager` when an expense category spending limit has been reached after adding a new expense. *(See: Notification specification)*
    - Show a notification when the user has enabled the notification preference in the App’s Settings.
    - Notification feature must be able to show up when the app runs on Android OS 13 (API 33) and above.
    - When the notification is clicked, direct the user to **ListExpensesByCategoryActivity** and show the appropriate expenses for that category.

8. Update the **dark mode theme** based on the value in `ListPreference`.

9. Address the following **comments from the QA team**:
    - Recent Cash Flow does not show the desired output. *(See: Home Screen specification)*
    - SnackBar is not showing, preventing the user from undoing the deleted item.

10. Write a **UI test** to validate that when users click *Show All Cash Flows*, the `recyclerview_cashflows` on **AllCashFlowsActivity** is displayed.

---

## **Things to Note**

Your submission must match the required criteria to be accepted by our Proctors.  
Any improvisation, enhancement, or addition of new features that are not asked for is not needed here.

Below are some items that you will need to avoid:

- Didn’t use `FilterUtils.getFilterQuery` to create a filterable query.
- The displayed **AllSummarySection** did not follow the Home Screen specification.
- Recent Transaction can’t display recent income/expense records correctly.
- **Expenses By Category list** can’t display the newest data correctly.
- **All Cash Flows list** can’t display data based on the filter correctly.
- **All Cash Flows list** can’t display the newest data correctly.
- Clicking **undo** on the SnackBar does not restore deleted items.
- Doesn’t name the `AddCashFlowActivity` layout `activity_add_cashflow.xml`.
- The option value for **Add Cash Flow’s category Spinner** is not from the query process to the database.
- Does not require the user to fill in all fields in **AddCashFlowActivity**.
- The notification feature shows up after adding an income record instead of an expense.
- The notification feature doesn’t show up when the app runs on Android OS 13 (API 33) and above.
- The displayed notification did not follow the **Notification specification**.
- Clicking the notification can’t direct the user to **ListExpensesByCategoryActivity** and show appropriate expenses in that category.
- App theme doesn’t match theme preferences when reopened.
- Having a **ZIP file inside another ZIP file** in your submission (multiple layers of ZIP files).
- Force-closed application.
- The project cannot be built.
- Submit files other than the Android Studio project.
- Submitting projects that are not your own work.
- Removing or modifying any code in the starter project/base code that is not required by the task. *(Example: renaming existing files or variables.)*

---

## **Submission Guidelines**

- Use **Android Studio**.
- Submit your work as an **archived Android Studio Project (ZIP)** folder.

---

## **Steps to Export Your Project Properly**

Before submitting the project, ensure you export it correctly using the following steps:

1. Open **Android Studio**.
2. Navigate to **File → Manage IDE Settings → Export to ZIP File...**.
3. Select a storage directory and click **OK**.
    - Using this method ensures the ZIP file size will be smaller compared to manually compressing through the file explorer.

---
