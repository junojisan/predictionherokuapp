package controllers.api;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;

import play.Configuration;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.api.PredictionService;

/**
 * 言語の予測を行うコントローラークラスです
 * @author jimaoka
 */
public class PredictionController extends Controller {
	private Configuration configuration;
	
	/**
	 * コンストラクタ
	 * @param configuration
	 * @return PredictionController
	 */
	@Inject
	public PredictionController(Configuration configuration){
		this.configuration = configuration;
	}

	/**
	 * 言語の予測のリクエストを処理します
	 * @param
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public Result predict(){
		String applicationName = configuration.getString("prediction.application.name");
		String modelId = configuration.getString("prediction.model_id");
		String projectId = configuration.getString("prediction.project_id");
		String serviceAcctEmail = configuration.getString("prediction.account_email");
		String serviceAcctKeyfile = configuration.getString("prediction.keyfile");
		
		JsonNode requestJson = request().body().asJson();
		ObjectNode result = Json.newObject();
		
		if(requestJson == null){
			result.put("status", "NG");
			result.put("message", "Expecting Json data");
			return badRequest(result);
		}
		
		String text = requestJson.findPath("text").textValue();
		if(text == null){
			result.put("status", "NG");
			result.put("message", "Missing parameter [text]");
			return badRequest(result);
		}
		
		PredictionService service;
		try {
			service = new PredictionService(applicationName, modelId, projectId, serviceAcctEmail, serviceAcctKeyfile);
			result.put("label", service.predict(text));
			return ok(result);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			result.put("message", e.getMessage() + sw.toString());
			return badRequest(result);
		}
	}
}
