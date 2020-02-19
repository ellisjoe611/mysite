package com.douzone.mysite.action.guestbook;

import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch (actionName) {
		case "insert":
			return new InsertAction();
		case "delete":
			return new DeleteAction();
		case "deleteform":
			return new DeleteFormAction();
		default:
			return new GuestbookAction();
		}
	}

}
