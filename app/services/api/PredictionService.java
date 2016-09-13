package services.api;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.prediction.Prediction;
import com.google.api.services.prediction.PredictionScopes;
import com.google.api.services.prediction.Prediction.Builder;
import com.google.api.services.prediction.model.Input;
import com.google.api.services.prediction.model.Output;
import com.google.api.services.prediction.model.Input.InputInput;
import com.google.api.services.storage.StorageScopes;

/**
 * 言語の予測を行うサービスクラスです
 * @author jimaoka
 *
 */
public class PredictionService {
	private final String modelId;
	private final String projectId;
	private final String serviceAcctEmail;
	private final String serviceAcctKeyfile;

	private JsonFactory jsonFactory;
	private HttpTransport httpTransport;
	private Prediction prediction;
	
	/**
	 * コンストラクタ
	 * @param applicationName
	 * @param modelId
	 * @param projectId
	 * @param serviceAcctEmail
	 * @param serviceAcctKeyfile
	 * @return PredictionService
	 * @throws Exception
	 */
	public PredictionService(String applicationName, String modelId,
			String projectId, String serviceAcctEmail, String serviceAcctKeyfile) throws Exception {
		this.modelId = modelId;
		this.projectId = projectId;
		this.serviceAcctEmail = serviceAcctEmail;
		this.serviceAcctKeyfile = serviceAcctKeyfile;
		
		jsonFactory = JacksonFactory.getDefaultInstance();
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = authorize();
		Builder builder = new Prediction.Builder(httpTransport, jsonFactory, credential);
		builder.setApplicationName(applicationName);
		prediction = builder.build();
	}
	
	/**
	 * Googleと認証を行います
	 * @return
	 * @throws Exception
	 */
	private GoogleCredential authorize() throws Exception{
		GoogleCredential.Builder builder = new GoogleCredential.Builder();
		builder.setTransport(httpTransport);
		builder.setJsonFactory(jsonFactory);
		builder.setServiceAccountId(serviceAcctEmail);
		builder.setServiceAccountPrivateKeyFromP12File(new File(serviceAcctKeyfile));
		builder.setServiceAccountScopes(Arrays.asList(PredictionScopes.PREDICTION, StorageScopes.DEVSTORAGE_READ_ONLY));
		return builder.build();
	}

	/**
	 * 言語の予測を行います
	 * @param text 予測を行うテキスト
	 * @return String 予測結果の言語
	 * @throws Exception
	 */
	public String predict(String text) throws Exception{
		Input input = new Input();
		InputInput inputInput = new InputInput();
		inputInput.setCsvInstance(Collections.<Object>singletonList(text));
		input.setInput(inputInput);
		Output output;
		output = prediction.trainedmodels().predict(projectId, modelId, input).execute();
		return output.getOutputLabel();
	}
}
