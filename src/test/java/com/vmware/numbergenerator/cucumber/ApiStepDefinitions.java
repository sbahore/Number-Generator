package com.vmware.numbergenerator.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ApiStepDefinitions {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	HttpPost postRequest;
	HttpGet getRequest;
	HttpResponse response;
	static String uuid;
	static String status;


	@Given("User gives {string} and {string} in the request")
	public void userGivesAndInTheRequest(String goal, String step) throws JSONException {
		postRequest = new HttpPost("http://localhost:8080/api/generate");
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("goal", Integer.valueOf(goal));
		jsonObj.put("step", Integer.valueOf(step));
		StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
		entity.setContentType("application/json");
		postRequest.setEntity(entity);
	}

	@When("User sends POST request to generate number sequence")
	public void userSendsPOSTRequestToGenerateNumberSequence() throws IOException {
		response = httpClient.execute(postRequest);
	}

	@Then("API should return status as {int}")
	public void apiShouldReturnStatusAs(int status) {
		Assertions.assertEquals(status, response.getStatusLine()
		        .getStatusCode());
	}

	@And("The response should have {string}")
	public void theResponseShouldHave(String field) throws IOException, JSONException {
		InputStream responseStream = response.getEntity()
		        .getContent();
		Scanner scanner = new Scanner(responseStream, "UTF-8");
		String responseString = scanner.useDelimiter("\\Z")
		        .next();
		JSONObject responseObj = new JSONObject(responseString);
		if (field.equals("task"))
			uuid = responseObj.get(field)
			        .toString();
		else if (field.equals("status"))
			status = responseObj.get(field)
			        .toString();
		Assertions.assertTrue(responseString.contains(field));
	}

	@Given("User gives uuid of task in the request")
	public void userGivesUuidOfTaskInTheRequest() {
		getRequest = new HttpGet("http://localhost:8080/api/tasks/" + uuid + "/status");
		getRequest.setHeader("content-type", "application/json");
	}

	@When("User sends GET request to get status of task")
	public void userSendsGETRequestToGetStatusOfTask() throws IOException {
		response = httpClient.execute(getRequest);
	}

	@Given("User gives uuid of task and get_numlist in the request")
	public void userGivesUuidOfTaskAndGet_numlistInTheRequest() throws URISyntaxException {
		URIBuilder builder = new URIBuilder("http://localhost:8080/api/tasks/" + uuid);
		builder.addParameter("action", "get_numlist");
		getRequest = new HttpGet(builder.build());
		getRequest.setHeader("content-type", "application/json");
	}

	@When("User sends GET request to get List of task")
	public void userSendsGETRequestToGetListOfTask() throws IOException, InterruptedException {
		Thread.sleep(15000);
		response = httpClient.execute(getRequest);
	}

	@And("The response should have a List of Numbers")
	public void theResponseShouldHaveAListOfNumbers() throws JSONException, IOException {
		InputStream responseStream = response.getEntity()
				.getContent();
		Scanner scanner = new Scanner(responseStream, "UTF-8");
		String responseString = scanner.useDelimiter("\\Z")
				.next();
		JSONObject responseObj = new JSONObject(responseString);
		Assertions.assertNotNull(responseObj);
	}
}
