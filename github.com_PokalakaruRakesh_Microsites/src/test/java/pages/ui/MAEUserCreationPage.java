// ADD THESE NEW METHODS TO EXISTING CLASS - DO NOT MODIFY EXISTING CODE

    /**
     * Complete add user flow: clicks 'Add User', fills all required details, selects roles, and saves.
     * This method assumes the user is already on the User Management tab for the correct account.
     *
     * @param email      Email and Username
     * @param firstName  First Name
     * @param lastName   Last Name
     */
    public void completeAddUserFlow(String email, String firstName, String lastName) {
        try {
            // Click 'Add User' button
            By addUserButton = By.xpath("//button[@data-testid='add-user']");
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(addUserButton), 20);
            driver.findElement(addUserButton).click();

            // Wait for Add User modal
            By addUserModalTitle = By.xpath("//div[contains(@class,'modal-content')]//h5[contains(text(),'Add User')]");
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(addUserModalTitle), 10);

            // Fill Email
            By emailInput = By.xpath("//input[@class='form-control' and @placeholder='Email' and @name='email']");
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(emailInput), 10);
            driver.findElement(emailInput).clear();
            driver.findElement(emailInput).sendKeys(email);

            // Fill Username (readonly, auto-filled, skip)
            // Fill First Name
            By firstNameInput = By.xpath("//input[@class='form-control' and @placeholder='First Name' and @name='firstName']");
            driver.findElement(firstNameInput).clear();
            driver.findElement(firstNameInput).sendKeys(firstName);

            // Fill Last Name
            By lastNameInput = By.xpath("//input[@class='form-control' and @placeholder='Last Name' and @name='lastName']");
            driver.findElement(lastNameInput).clear();
            driver.findElement(lastNameInput).sendKeys(lastName);

            // Select Compass Roles: Compass User Administrator, Compass User
            By compassUserAdminCheckbox = By.xpath("//input[@data-testid='role-input-1']");
            By compassUserCheckbox = By.xpath("//input[@data-testid='role-input-2']");
            if (!driver.findElement(compassUserAdminCheckbox).isSelected()) {
                driver.findElement(compassUserAdminCheckbox).click();
            }
            if (!driver.findElement(compassUserCheckbox).isSelected()) {
                driver.findElement(compassUserCheckbox).click();
            }

            // Select Administration Role: User Admin
            By userAdminCheckbox = By.xpath("//input[@data-testid='role-input-14']");
            if (!driver.findElement(userAdminCheckbox).isSelected()) {
                driver.findElement(userAdminCheckbox).click();
            }

            // Click Save button
            By saveButton = By.xpath("//div[contains(@class,'modal-footer')]/button[contains(text(),'Save')]");
            WaitStatementUtils.waitForElementToBeClickable(driver, driver.findElement(saveButton));
            driver.findElement(saveButton).click();

            // Wait for modal to close (wait for absence of modal)
            WaitStatementUtils.explicitWaitForInvisibility(driver, addUserModalTitle, 10);
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshotForAllure(driver);
            throw new RuntimeException("Failed to complete add user flow: " + e.getMessage(), e);
        }
    }

    /**
     * Verifies if the given email is present in the Manage Users table (after user creation).
     * @param email Email to verify
     * @return true if email is found, false otherwise
     */
    public boolean isUserEmailPresentInManageUsers(String email) {
        try {
            // Wait for user table to be present
            By userTable = By.xpath("//table[@class='table astm-table dataTable table-bordered table-striped']");
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(userTable), 15);

            // Email cell: <span id='view-...'>email</span>
            By emailCell = By.xpath("//span[contains(@id,'view-') and normalize-space(text())='" + email + "']");
            WaitStatementUtils.explicitWaitForVisibility(driver, driver.findElement(emailCell), 10);
            return driver.findElement(emailCell).isDisplayed();
        } catch (Exception e) {
            ScreenshotUtil.takeScreenshotForAllure(driver);
            return false;
        }
    }
