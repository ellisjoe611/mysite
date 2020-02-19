package com.douzone.mysite.action.board;

import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		switch(actionName) {
		case "searchtitle":
			return new SearchTitleAction();
		case "insertform":
			return new InsertFormAction();
		case "insert":
			return new InsertAction();
		case "modifyform":
			return new ModifyFormAction();
		case "modify":
			return new ModifyAction();
		case "replyform":
			return new ReplyFormAction();
		case "reply":
			return new ReplyAction();
		case "viewform":
			return new ViewFormAction();
		case "delete":
			return new DeleteAction();
		default:
			return new BoardAction();
		}
	}

}
