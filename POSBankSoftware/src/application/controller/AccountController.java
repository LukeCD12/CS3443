package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.model.Accounts;
import application.model.ShowData;
import application.model.Users;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AccountController  implements Initializable, SubController {
	
	MainController mc;
	Users user;
	ShowData data;
	
	boolean userFlg = false;

	@FXML
	private TextField NewName, NewBalance;
	
	@FXML
	private Button AddAccount, cancelAccount;
	
	
	public void createNewAccount(ActionEvent event) {
		user.addAccount(new Accounts(NewName.getText(), "", Double.parseDouble(NewBalance.getText()), ""));
		
		mc.updateView(this, MainController.mainView, MainController.mainX, MainController.mainY);
	}
	
	public void updateAccount() {
		Accounts acct = (Accounts)data;
		
		acct.setAccNum(NewName.getText());
		acct.setCurrBalance(Double.parseDouble(NewBalance.getText()));
		
		mc.updateView(this, MainController.mainView, MainController.mainX, MainController.mainY);
		
	}
	
	public boolean validateTextFields() {
		if (!NewName.getText().matches("[A-Za-z0-9]+.*")) {
			return false;
		} else if (!NewBalance.getText().matches("[0-9]+(.[0-9]{2})*")) {
			return false;
		} else {
			return true;
		}
	}
	
	
	@Override
	public void onLoad(ShowData data, MainController mc) {
		// TODO Auto-generated method stub
		this.mc = mc;
		
		if (!(data instanceof Users)) {
//			mc.updateView(this, MainController.mainView, MainController.mainX, MainController.mainY);
			this.user = mc.getCurrUser();
			this.data = data;
			
			NewName.setText(data.getName());
			NewBalance.setText(data.getTotal());
			
		} else {
			this.user = (Users)data;
			this.userFlg = true;
		}	
		
		NewBalance.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent key) {
				if (key.getCode() == KeyCode.ENTER) {
					if (!validateTextFields()) {
						NewName.requestFocus();
					} else {
						if (AccountController.this.userFlg)
							createNewAccount(new ActionEvent());
						else
							AccountController.this.updateAccount();
					}
				}
			}
		});
		
		AddAccount.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (!validateTextFields()) {
					NewName.requestFocus();
				} else {
					if (AccountController.this.userFlg)
						createNewAccount(event);
					else
						AccountController.this.updateAccount();
				}
			}
		});
		
		cancelAccount.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				mc.updateView(AccountController.this, MainController.mainView, MainController.mainX, MainController.mainY);
			}
		});
		
	}

	@Override
	public ShowData onExit() {
		// TODO Auto-generated method stub
		return this.user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
