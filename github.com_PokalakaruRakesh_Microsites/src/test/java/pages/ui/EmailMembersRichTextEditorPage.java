// ADD THESE NEW METHODS TO EXISTING CLASS - DO NOT MODIFY EXISTING CODE

    // Locators for Attachment Section
    private By fileInput = By.xpath("//input[@type='file']");
    private By attachedFilesList = By.xpath("//ul[@class='attached-files-list']");
    private By clickToOpenButton = By.xpath("//div[text()='Click to Open']");
    private By dragAndDropText = By.xpath("//div[text()='Drag and Drop File Here']");
    private By noFilesAttachedText = By.xpath("//li[text()='No files attached']");
    private By attachedFilesLabel = By.xpath("//div/b[text()='Attached Files']");
    private By uploadFileLabel = By.xpath("//input[@type='file']/ancestor::div[contains(@class,'drag-area')]//div[text()='Click to Open']");

    /**
     * Verifies the attachment section design and functionality including labels and file upload.
     * This is a comprehensive method for the test case: TCD_FT_05_FR-3
     * @param filePath Absolute path to the file to upload (e.g., sample.pdf)
     */
    public void verifyAttachmentSectionDesignAndFunctionality(String filePath) {
        // Wait for the attachment section to be visible
        WaitStatementUtils.explicitWaitForVisibility(driver, clickToOpenButton, 10);
        WaitStatementUtils.explicitWaitForVisibility(driver, dragAndDropText, 10);
        WaitStatementUtils.explicitWaitForVisibility(driver, attachedFilesLabel, 10);

        // Verify 'Attached Files' label is displayed
        if (!driver.findElement(attachedFilesLabel).isDisplayed()) {
            throw new AssertionError("'Attached Files' label is not displayed in the attachment section.");
        }

        // Verify 'Upload File' (Click to Open) label is displayed
        if (!driver.findElement(clickToOpenButton).isDisplayed()) {
            throw new AssertionError("'Click to Open' (Upload File) label is not displayed in the attachment section.");
        }

        // Verify 'Drag and Drop File Here' text is displayed
        if (!driver.findElement(dragAndDropText).isDisplayed()) {
            throw new AssertionError("'Drag and Drop File Here' text is not displayed in the attachment section.");
        }

        // Verify 'No files attached' text is displayed initially
        if (!driver.findElement(noFilesAttachedText).isDisplayed()) {
            throw new AssertionError("'No files attached' text is not displayed before upload.");
        }

        // Upload the file
        WebElement fileInputElem = driver.findElement(fileInput);
        fileInputElem.sendKeys(filePath);

        // Wait for the file to appear in the attached files list
        WaitStatementUtils.explicitWaitForVisibility(driver, attachedFilesList, 10);
        WebElement attachedList = driver.findElement(attachedFilesList);
        boolean fileAttached = attachedList.getText().toLowerCase().contains(getFileNameFromPath(filePath).toLowerCase());
        if (!fileAttached) {
            throw new AssertionError("Uploaded file name not found in attached files list.");
        }
    }

    // Utility method to extract file name from path
    private String getFileNameFromPath(String filePath) {
        if (filePath == null) return "";
        return filePath.substring(filePath.replace("\\", "/").lastIndexOf("/") + 1);
    }
