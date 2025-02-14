package com.serotonin.mango.rt.maint.work;

import com.serotonin.mango.util.SendEmailConfig;
import com.serotonin.mango.util.SendEmailData;
import com.serotonin.mango.web.email.EmailTimeoutSender;

public class EmailAfterWorkItem extends AbstractBeforeAfterWorkItem {

    private final SendEmailData sendEmailData;
    private final SendEmailConfig sendEmailConfig;
    private final AfterWork afterWork;
    private final WorkItemDetails workItemDetails;

    protected EmailAfterWorkItem(SendEmailData sendEmailData, SendEmailConfig sendEmailConfig, AfterWork afterWork,
                                 WorkItemDetails workItemDetails) {
        this.afterWork = afterWork;
        this.sendEmailData = sendEmailData;
        this.sendEmailConfig = sendEmailConfig;
        this.workItemDetails = workItemDetails;
    }

    public static WorkItem newInstance(SendEmailData sendEmailData, SendEmailConfig sendEmailConfig,
                                       AfterWork afterWork, WorkItemDetails workItemDetails) {
        return new EmailAfterWorkItem(sendEmailData, sendEmailConfig, afterWork, workItemDetails);
    }

    @Override
    public void work() {
        EmailTimeoutSender emailSender = new EmailTimeoutSender(sendEmailConfig);
        emailSender.send(sendEmailData.getFromAddress(), sendEmailData.getToAddresses(),
                sendEmailData.getContent().getSubject(), sendEmailData.getContent());
    }

    @Override
    public void workSuccess() {
        afterWork.workSuccess();
    }

    @Override
    public void workFail(Exception exception) {
        afterWork.workFail(exception);
    }

    @Override
    public int getPriority() {
        return WorkItem.PRIORITY_MEDIUM;
    }

    @Override
    public String toString() {
        return "EmailAfterWorkItem{" +
                "sendEmailData=" + sendEmailData +
                ", sendEmailConfig=" + sendEmailConfig +
                ", details='" + workItemDetails.getDetails() +
                "'}";
    }

    @Override
    public String getDetails() {
        return this.toString();
    }
}
