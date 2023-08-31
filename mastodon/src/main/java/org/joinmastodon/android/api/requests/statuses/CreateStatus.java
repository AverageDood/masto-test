package org.joinmastodon.android.api.requests.statuses;

import org.joinmastodon.android.api.MastodonAPIRequest;
import org.joinmastodon.android.model.ContentType;
import org.joinmastodon.android.model.ScheduledStatus;
import org.joinmastodon.android.model.Status;
import org.joinmastodon.android.model.StatusPrivacy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CreateStatus extends MastodonAPIRequest<Status>{
	public static long EPOCH_OF_THE_YEAR_FIVE_THOUSAND=95617584000000L;
	public static final Instant DRAFTS_AFTER_INSTANT=Instant.ofEpochMilli(EPOCH_OF_THE_YEAR_FIVE_THOUSAND - 1) /* end of 4999 */;

	public static Instant getDraftInstant() {
		return DRAFTS_AFTER_INSTANT.plusMillis(System.currentTimeMillis());
	}

	public CreateStatus(CreateStatus.Request req, String uuid){
		super(HttpMethod.POST, "/statuses", Status.class);
		setRequestBody(req);
		addHeader("Idempotency-Key", uuid);
	}

	public static class Scheduled extends MastodonAPIRequest<ScheduledStatus>{
		public Scheduled(CreateStatus.Request req, String uuid){
			super(HttpMethod.POST, "/statuses", ScheduledStatus.class);
			setRequestBody(req);
			addHeader("Idempotency-Key", uuid);
		}
	}

	public static class Request{
		public String status;
		public List<String> mediaIds;
		public Poll poll;
		public String inReplyToId;
		public boolean sensitive;
		public boolean localOnly;
		public String spoilerText;
		public StatusPrivacy visibility;
		public Instant scheduledAt;
		public String language;

		public String quoteId;
		public ContentType contentType;

		public static class Poll{
			public ArrayList<String> options=new ArrayList<>();
			public int expiresIn;
			public boolean multiple;
			public boolean hideTotals;
		}
	}
}
