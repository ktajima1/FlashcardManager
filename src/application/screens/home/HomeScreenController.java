package application.screens.home;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class HomeScreenController {

    @FXML
    private TabPane courseList;

    @FXML
    private Button deleteCourseBtn;

    @FXML
    private Button newCourseBtn;

    @FXML
    private Button renameCourseBtn;

    @FXML
    private VBox rename;

    @FXML
    private TextField newCourseNameInput;



    @FXML
    void onDeleteTabClick(MouseEvent event) {
        if (courseList.getTabs().size() > 0) {
            int selectedIndex = courseList.getSelectionModel().getSelectedIndex();
            courseList.getTabs().remove(selectedIndex);
        }
    }

    @FXML
    void onNewTabClick(MouseEvent event) {
        Tab blankTab = new Tab();
        blankTab.setText("test tab " + courseList.getTabs().size());
        courseList.getTabs().add(blankTab);
        courseList.getSelectionModel().select(blankTab);
        nameTab();
    }

    @FXML
    void onRenameTabClick(MouseEvent event) {
        if (courseList.getTabs().size() > 0) {
            nameTab();
        } 
    }

    void nameTab() {
        rename.setVisible(true);
    }

    @FXML
    void submitNewCourseName(MouseEvent event) {
        if (newCourseNameInput.getText() != null) {
            int selectedIndex = courseList.getSelectionModel().getSelectedIndex();
            Tab currentTab = courseList.getTabs().get(selectedIndex);

            String newName = newCourseNameInput.getText();
            currentTab.setText(newName);
            rename.setVisible(false);
        } else {
            //show error of course name is empty
        }
    }

    @FXML
    void signOut(MouseEvent event) {
        Main.loadScreen(event, "screens/login/LoginScreen.fxml", "LoginScreen");
    }

}
