package com.fms.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fms.common.entity.Approval;
import com.fms.common.entity.Notification;
import com.fms.common.entity.Request;
import com.fms.common.ui.responses.NotificationsList;
import com.fms.ems.entity.ApproverLevel;

@Service
public class NotificationService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpoint.nms}")
	private String nmsEndpoint;

	@Autowired
	ProjectService projectService;

	@Autowired
	ApproverLevelService approverLevelService;

	public NotificationsList getAllNotificationsByUserId(String userId) {
		return restTemplate.getForObject(nmsEndpoint + "/notification/" + userId, NotificationsList.class);
	}

	public ResponseEntity<Notification> sendAppNotification(Notification notification) {
		return restTemplate.exchange(nmsEndpoint + "/notification/send-app/" + notification.getTriggeredTo(),
				HttpMethod.POST, new HttpEntity<Notification>(notification), Notification.class);
	}

	public Notification setNotificationAsSeen(String notificationUuid) {
		return restTemplate.getForObject(nmsEndpoint + "/notification/" + notificationUuid + "/seen",
				Notification.class);
	}

	public void mapAndSendAppNotificationForApproverLevelAssigned(ApproverLevel approverLevel) {
		Notification notification = new Notification();
		notification.setTriggeredBy(0);
		notification.setTriggeredTo(approverLevel.getApprover().getUserId());
		notification.setMessage("You are assigned as Approver to Project: "
				+ approverLevel.getProject().getProjectName() + " at Level: " + approverLevel.getLevel());

		sendAppNotification(notification);
	}

	public void mapAndSendAppNotificationForRequestRaised(Request request) {

		List<ApproverLevel> approverLevels = approverLevelService.getApproverLevelsByProjectId(request.getProjectId());
		for (ApproverLevel approverLevel : approverLevels) {
			Notification notification = new Notification();
			notification.setTriggeredBy(0);
			notification.setTriggeredTo(approverLevel.getApprover().getUserId());
			notification.setMessage("Request has been raised on Assigned Project: " + request.getProjectName());

			sendAppNotification(notification);
		}

		mapAndSendAppNotificationForRequestAssignedAfterLevel(request, 0);
	}

	public void mapAndSendAppNotificationForRequestAssignedAfterLevel(Request request, int level) {
		List<ApproverLevel> approverLevels = approverLevelService.getApproverLevelsByProjectId(request.getProjectId());
		for (ApproverLevel approverLevel : approverLevels) {
			if ((level + 1) == approverLevel.getLevel()) {

				Notification notification = new Notification();
				notification.setTriggeredBy(0);
				notification.setTriggeredTo(approverLevel.getApprover().getUserId());
				notification.setMessage("Request is assigned to you for approval on Project: "
						+ request.getProjectName() + " with Deadline: " + request.getDeadlineDatetime().toLocalDate());

				sendAppNotification(notification);
				break;
			}
		}

	}

	public void mapAndSendAppNotificationForRequestApproved(Request request, Approval approval) {
		Notification notification = new Notification();
		notification.setTriggeredBy(0);
		notification.setTriggeredTo(request.getRaisedBy());
		notification.setMessage("Your Request on Project: " + request.getProjectName() + " has been APPROVED by: "
				+ approval.getApproverName() + " with Comments: " + approval.getComments());

		sendAppNotification(notification);
	}

	public void mapAndSendAppNotificationForRequestRejected(Request request, Approval approval) {
		Notification notification = new Notification();
		notification.setTriggeredBy(0);
		notification.setTriggeredTo(request.getRaisedBy());
		notification.setMessage("Your Request on Project: " + request.getProjectName() + " has been REJECTED by: "
				+ approval.getApproverName() + " with Comments: " + approval.getComments());

		sendAppNotification(notification);
	}

	public void mapAndSendAppNotificationForRequestFullyApproved(Request request) {
		Notification notification = new Notification();
		notification.setTriggeredBy(0);
		notification.setTriggeredTo(request.getRaisedBy());
		notification.setMessage("Your Request on Project: " + request.getProjectName()
				+ " has been FULLY APPROVED and sent for Payment");

		sendAppNotification(notification);
	}

}
