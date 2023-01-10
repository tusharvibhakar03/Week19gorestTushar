package com.gorest.cucumber.steps;

import com.gorest.steps.UsersSteps;
import com.gorest.utils.PropertyReader;
import com.gorest.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.hamcrest.Matchers;
import org.junit.Assert;

import java.util.HashMap;

import static com.gorest.cucumber.steps.MyStepdefs.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;

public class CrudStepdefs {

    static ValidatableResponse response;
    static String token = PropertyReader.getInstance().getProperty("token");
    static int userID;
    static String name = null;

    @Steps
    UsersSteps usersSteps;

    @Given("^Gorest application is running$")
    public void gorestApplicationIsRunning() {
    }

    @When("^When I create a new user by providing the information name \"([^\"]*)\" email \"([^\"]*)\" gender \"([^\"]*)\" status \"([^\"]*)\"token\"([^\"]*)\"$")
    public void whenICreateANewUserByProvidingTheInformationNameEmailGenderStatusToken(String name1, String email, String gender, String status, String _token) {
        name = name1 + TestUtils.getRandomValue();
        email = TestUtils.getRandomValue() + email;
        response = usersSteps.createUser(name, gender, email, status, token);

    }

    @Then("^I verify that the user with \"([^\"]*)\" is created$")
    public void iVerifyThatTheUserWithIsCreated(String name) {
        response.statusCode(201);
        userID = response.log().all().extract().body().path("id");
        System.out.println(userID);

    }

    @And("^I update the user with information name \"([^\"]*)\" email \"([^\"]*)\" gender \"([^\"]*)\" status \"([^\"]*)\"$")
    public void iUpdateTheUserWithInformationNameEmailGenderStatus(String name1, String email, String gender, String status) {
        name = name1 + "_abc";
       email = TestUtils.getRandomValue() + email;
        response = usersSteps.updateUser(name,gender,userID,email, status, token);
    }

    @And("^The user is updated successfully$")
    public void theUserIsUpdatedSuccessfully() {
//        response.statusCode(200).body("id", Matchers.equalTo(userID),
//                "name", Matchers.equalTo(name), "email", Matchers.equalTo(email),
//                "gender", Matchers.equalTo(gender), "status", Matchers.equalTo(status));

    }

    @And("^I delete the user with userId$")
    public void iDeleteTheUserWithUserId() {
        response = usersSteps.deleteUser(userID,token);
    }

    @Then("^The user deleted successfully from the application$")
    public void theUserDeletedSuccessfullyFromTheApplication() {
        response.statusCode(204);
    }
}
