package io.swagger.v3.core.serialization;

import io.swagger.v3.core.matchers.SerializationMatchers;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.core.util.Json31;
import io.swagger.v3.core.util.ResourceUtils;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.core.util.Yaml31;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.callbacks.Callback;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.links.Link;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.checkerframework.checker.units.qual.C;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class OpenAPI3_1SerializationTest {

    @Test
    public void testSerializePetstore() throws Exception {

        final String jsonString = ResourceUtils.loadClassResource(getClass(), "specFiles/3.1.0/petstore-3.1.yaml");
        final OpenAPI swagger = Yaml31.mapper().readValue(jsonString, OpenAPI.class);
        assertNotNull(swagger);
        assertEquals(swagger.getInfo().getLicense().getIdentifier(), "test");
        SerializationMatchers.assertEqualsToYaml31(swagger, "openapi: 3.1.0\n" +
                "info:\n" +
                "  title: Swagger Petstore\n" +
                "  license:\n" +
                "    name: MIT\n" +
                "    identifier: test\n" +
                "  version: 1.0.0\n" +
                "servers:\n" +
                "- url: http://petstore.swagger.io/v1\n" +
                "paths:\n" +
                "  /pets:\n" +
                "    get:\n" +
                "      tags:\n" +
                "      - pets\n" +
                "      summary: List all pets\n" +
                "      operationId: listPets\n" +
                "      parameters:\n" +
                "      - name: limit\n" +
                "        in: query\n" +
                "        description: How many items to return at one time (max 100)\n" +
                "        required: false\n" +
                "        schema:\n" +
                "          type: integer\n" +
                "          format: int32\n" +
                "      responses:\n" +
                "        \"200\":\n" +
                "          description: An paged array of pets\n" +
                "          headers:\n" +
                "            x-next:\n" +
                "              description: A link to the next page of responses\n" +
                "              schema:\n" +
                "                type: string\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/Pets'\n" +
                "        default:\n" +
                "          description: unexpected error\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/Error'\n" +
                "    post:\n" +
                "      tags:\n" +
                "      - pets\n" +
                "      summary: Create a pet\n" +
                "      operationId: createPets\n" +
                "      responses:\n" +
                "        \"201\":\n" +
                "          description: Null response\n" +
                "        default:\n" +
                "          description: unexpected error\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/Error'\n" +
                "  /pets/{petId}:\n" +
                "    get:\n" +
                "      tags:\n" +
                "      - pets\n" +
                "      summary: Info for a specific pet\n" +
                "      operationId: showPetById\n" +
                "      parameters:\n" +
                "      - name: petId\n" +
                "        in: path\n" +
                "        description: The id of the pet to retrieve\n" +
                "        required: true\n" +
                "        schema:\n" +
                "          type: string\n" +
                "      responses:\n" +
                "        \"200\":\n" +
                "          description: Expected response to a valid request\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/Pets'\n" +
                "        default:\n" +
                "          description: unexpected error\n" +
                "          content:\n" +
                "            application/json:\n" +
                "              schema:\n" +
                "                $ref: '#/components/schemas/Error'\n" +
                "components:\n" +
                "  schemas:\n" +
                "    Pet:\n" +
                "      required:\n" +
                "      - id\n" +
                "      - name\n" +
                "      properties:\n" +
                "        id:\n" +
                "          type: integer\n" +
                "          format: int64\n" +
                "        name:\n" +
                "          type:\n" +
                "          - string\n" +
                "          - integer\n" +
                "        tag:\n" +
                "          type: string\n" +
                "    Pets:\n" +
                "      type: array\n" +
                "      items:\n" +
                "        $ref: '#/components/schemas/Pet'\n" +
                "    Error:\n" +
                "      required:\n" +
                "      - code\n" +
                "      - message\n" +
                "      properties:\n" +
                "        code:\n" +
                "          type: integer\n" +
                "          format: int32\n" +
                "        message:\n" +
                "          type: string\n" +
                "webhooks:\n" +
                "  newPet:\n" +
                "    post:\n" +
                "      requestBody:\n" +
                "        description: Information about a new pet in the system\n" +
                "        content:\n" +
                "          application/json:\n" +
                "            schema:\n" +
                "              $ref: '#/components/schemas/Pet'\n" +
                "      responses:\n" +
                "        \"200\":\n" +
                "          description: Return a 200 status to indicate that the data was received\n" +
                "            successfully");
        SerializationMatchers.assertEqualsToJson31(swagger, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"info\" : {\n" +
                "    \"title\" : \"Swagger Petstore\",\n" +
                "    \"license\" : {\n" +
                "      \"name\" : \"MIT\",\n" +
                "      \"identifier\" : \"test\"\n" +
                "    },\n" +
                "    \"version\" : \"1.0.0\"\n" +
                "  },\n" +
                "  \"servers\" : [ {\n" +
                "    \"url\" : \"http://petstore.swagger.io/v1\"\n" +
                "  } ],\n" +
                "  \"paths\" : {\n" +
                "    \"/pets\" : {\n" +
                "      \"get\" : {\n" +
                "        \"tags\" : [ \"pets\" ],\n" +
                "        \"summary\" : \"List all pets\",\n" +
                "        \"operationId\" : \"listPets\",\n" +
                "        \"parameters\" : [ {\n" +
                "          \"name\" : \"limit\",\n" +
                "          \"in\" : \"query\",\n" +
                "          \"description\" : \"How many items to return at one time (max 100)\",\n" +
                "          \"required\" : false,\n" +
                "          \"schema\" : {\n" +
                "            \"type\" : \"integer\",\n" +
                "            \"format\" : \"int32\"\n" +
                "          }\n" +
                "        } ],\n" +
                "        \"responses\" : {\n" +
                "          \"200\" : {\n" +
                "            \"description\" : \"An paged array of pets\",\n" +
                "            \"headers\" : {\n" +
                "              \"x-next\" : {\n" +
                "                \"description\" : \"A link to the next page of responses\",\n" +
                "                \"schema\" : {\n" +
                "                  \"type\" : \"string\"\n" +
                "                }\n" +
                "              }\n" +
                "            },\n" +
                "            \"content\" : {\n" +
                "              \"application/json\" : {\n" +
                "                \"schema\" : {\n" +
                "                  \"$ref\" : \"#/components/schemas/Pets\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"default\" : {\n" +
                "            \"description\" : \"unexpected error\",\n" +
                "            \"content\" : {\n" +
                "              \"application/json\" : {\n" +
                "                \"schema\" : {\n" +
                "                  \"$ref\" : \"#/components/schemas/Error\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"post\" : {\n" +
                "        \"tags\" : [ \"pets\" ],\n" +
                "        \"summary\" : \"Create a pet\",\n" +
                "        \"operationId\" : \"createPets\",\n" +
                "        \"responses\" : {\n" +
                "          \"201\" : {\n" +
                "            \"description\" : \"Null response\"\n" +
                "          },\n" +
                "          \"default\" : {\n" +
                "            \"description\" : \"unexpected error\",\n" +
                "            \"content\" : {\n" +
                "              \"application/json\" : {\n" +
                "                \"schema\" : {\n" +
                "                  \"$ref\" : \"#/components/schemas/Error\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/pets/{petId}\" : {\n" +
                "      \"get\" : {\n" +
                "        \"tags\" : [ \"pets\" ],\n" +
                "        \"summary\" : \"Info for a specific pet\",\n" +
                "        \"operationId\" : \"showPetById\",\n" +
                "        \"parameters\" : [ {\n" +
                "          \"name\" : \"petId\",\n" +
                "          \"in\" : \"path\",\n" +
                "          \"description\" : \"The id of the pet to retrieve\",\n" +
                "          \"required\" : true,\n" +
                "          \"schema\" : {\n" +
                "            \"type\" : \"string\"\n" +
                "          }\n" +
                "        } ],\n" +
                "        \"responses\" : {\n" +
                "          \"200\" : {\n" +
                "            \"description\" : \"Expected response to a valid request\",\n" +
                "            \"content\" : {\n" +
                "              \"application/json\" : {\n" +
                "                \"schema\" : {\n" +
                "                  \"$ref\" : \"#/components/schemas/Pets\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"default\" : {\n" +
                "            \"description\" : \"unexpected error\",\n" +
                "            \"content\" : {\n" +
                "              \"application/json\" : {\n" +
                "                \"schema\" : {\n" +
                "                  \"$ref\" : \"#/components/schemas/Error\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"schemas\" : {\n" +
                "      \"Pet\" : {\n" +
                "        \"required\" : [ \"id\", \"name\" ],\n" +
                "        \"properties\" : {\n" +
                "          \"id\" : {\n" +
                "            \"type\" : \"integer\",\n" +
                "            \"format\" : \"int64\"\n" +
                "          },\n" +
                "          \"name\" : {\n" +
                "            \"type\" : [\"string\", \"integer\"]\n" +
                "          },\n" +
                "          \"tag\" : {\n" +
                "            \"type\" : \"string\"\n" +
                "          }\n" +
                "        }\n" +
                "      },\n" +
                "      \"Pets\" : {\n" +
                "        \"type\" : \"array\",\n" +
                "        \"items\" : {\n" +
                "          \"$ref\" : \"#/components/schemas/Pet\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"Error\" : {\n" +
                "        \"required\" : [ \"code\", \"message\" ],\n" +
                "        \"properties\" : {\n" +
                "          \"code\" : {\n" +
                "            \"type\" : \"integer\",\n" +
                "            \"format\" : \"int32\"\n" +
                "          },\n" +
                "          \"message\" : {\n" +
                "            \"type\" : \"string\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"webhooks\" : {\n" +
                "    \"newPet\" : {\n" +
                "      \"post\" : {\n" +
                "        \"requestBody\" : {\n" +
                "          \"description\" : \"Information about a new pet in the system\",\n" +
                "          \"content\" : {\n" +
                "            \"application/json\" : {\n" +
                "              \"schema\" : {\n" +
                "                \"$ref\" : \"#/components/schemas/Pet\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        },\n" +
                "        \"responses\" : {\n" +
                "          \"200\" : {\n" +
                "            \"description\" : \"Return a 200 status to indicate that the data was received successfully\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

    }

    @Test
    public void testInfoSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .info(new Info()
                        .title("Title test")
                        .description("This is a description for test")
                        .summary("Test Summary")
                        .version("1.0.0")
                        .termsOfService("https://test.term.of.services")
                        .contact(new Contact()
                                .name("Test Contact")
                                .url("https://test.contact.url")
                                .email("test@email.com"))
                        .license(new License()
                                .name("test license")
                                .url("https://test.license.com")
                                .identifier("swagger")));
        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "info:\n" +
                "  title: Title test\n" +
                "  description: This is a description for test\n" +
                "  summary: Test Summary\n" +
                "  termsOfService: https://test.term.of.services\n" +
                "  contact:\n" +
                "    name: Test Contact\n" +
                "    url: https://test.contact.url\n" +
                "    email: test@email.com\n" +
                "  license:\n" +
                "    name: test license\n" +
                "    url: https://test.license.com\n" +
                "    identifier: swagger\n" +
                "  version: 1.0.0");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"info\" : {\n" +
                "    \"title\" : \"Title test\",\n" +
                "    \"description\" : \"This is a description for test\",\n" +
                "    \"summary\" : \"Test Summary\",\n" +
                "    \"termsOfService\" : \"https://test.term.of.services\",\n" +
                "    \"contact\" : {\n" +
                "      \"name\" : \"Test Contact\",\n" +
                "      \"url\" : \"https://test.contact.url\",\n" +
                "      \"email\" : \"test@email.com\"\n" +
                "    },\n" +
                "    \"license\" : {\n" +
                "      \"name\" : \"test license\",\n" +
                "      \"url\" : \"https://test.license.com\",\n" +
                "      \"identifier\" : \"swagger\"\n" +
                "    },\n" +
                "    \"version\" : \"1.0.0\"\n" +
                "  }\n" +
                "}");

        openAPI.setOpenapi("3.0.3");
        SerializationMatchers.assertEqualsToYaml(openAPI, "openapi: 3.0.3\n" +
                "info:\n" +
                "  title: Title test\n" +
                "  description: This is a description for test\n" +
                "  termsOfService: https://test.term.of.services\n" +
                "  contact:\n" +
                "    name: Test Contact\n" +
                "    url: https://test.contact.url\n" +
                "    email: test@email.com\n" +
                "  license:\n" +
                "    name: test license\n" +
                "    url: https://test.license.com\n" +
                "  version: 1.0.0");

        SerializationMatchers.assertEqualsToJson(openAPI, "{\n" +
                "  \"openapi\" : \"3.0.3\",\n" +
                "  \"info\" : {\n" +
                "    \"title\" : \"Title test\",\n" +
                "    \"description\" : \"This is a description for test\",\n" +
                "    \"termsOfService\" : \"https://test.term.of.services\",\n" +
                "    \"contact\" : {\n" +
                "      \"name\" : \"Test Contact\",\n" +
                "      \"url\" : \"https://test.contact.url\",\n" +
                "      \"email\" : \"test@email.com\"\n" +
                "    },\n" +
                "    \"license\" : {\n" +
                "      \"name\" : \"test license\",\n" +
                "      \"url\" : \"https://test.license.com\"\n" +
                "    },\n" +
                "    \"version\" : \"1.0.0\"\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testWebHooksSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .addWebhooks("hook", new PathItem()
                        .description("test path hook")
                        .get(new Operation()
                                .operationId("testHookOperation")
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("test response description")))));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "webhooks:\n" +
                "  hook:\n" +
                "    description: test path hook\n" +
                "    get:\n" +
                "      operationId: testHookOperation\n" +
                "      responses:\n" +
                "        \"200\":\n" +
                "          description: test response description");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"webhooks\" : {\n" +
                "    \"hook\" : {\n" +
                "      \"description\" : \"test path hook\",\n" +
                "      \"get\" : {\n" +
                "        \"operationId\" : \"testHookOperation\",\n" +
                "        \"responses\" : {\n" +
                "          \"200\" : {\n" +
                "            \"description\" : \"test response description\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
        openAPI.setOpenapi("3.0.3");
        SerializationMatchers.assertEqualsToYaml(openAPI, "openapi: 3.0.3");
        SerializationMatchers.assertEqualsToJson(openAPI, "{\n" +
                "  \"openapi\" : \"3.0.3\"\n}");

    }

    @Test
    public void testComponentPathItemsSerialization() {
        Schema schema = new StringSchema();
        schema.addType(schema.getType());
        OpenAPI openAPI = new OpenAPI().openapi("3.1.0").components(new Components()
                .addSchemas("stringTest", schema)
                .addPathItems("/pathTest", new PathItem()
                        .description("test path item")
                        .get(new Operation()
                                .operationId("testPathItem")
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("response description")))))
                .addResponses("201", new ApiResponse()
                        .description("api response description")
                        .summary("api response summary"))
                .addParameters("param", new Parameter()
                        .in("query")
                        .description("parameter description")
                        .summary("parameter summary")
                        .schema(schema))
                .addExamples("example", new Example()
                        .summary("example summary")
                        .value("This is an example/")
                        .description("example description"))
                .addRequestBodies("body", new RequestBody()
                        .content(new Content()
                                .addMediaType("application/json", new MediaType()
                                        .schema(new ObjectSchema()))))
                .addHeaders("test-head", new Header()
                        .description("test header description")
                        .summary("test header summary"))
                .addSecuritySchemes("basic", new SecurityScheme()
                        .in(SecurityScheme.In.HEADER)
                        .scheme("http")
                        .description("ref security description")
                        .summary("ref security summary"))
                .addLinks("Link", new Link()
                        .operationRef("#/paths/~12.0~1repositories~1{username}/get"))
                .addCallbacks("TestCallback", new Callback().addPathItem("{$request.query.queryUrl}", new PathItem()
                        .description("test path item")
                        .post(new Operation()
                                .operationId("testPathItem")))));

        Yaml31.prettyPrint(openAPI);

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "components:\n" +
                "  schemas:\n" +
                "    stringTest:\n" +
                "      type: string\n" +
                "  responses:\n" +
                "    \"201\":\n" +
                "      description: api response description\n" +
                "      summary: api response summary\n" +
                "  parameters:\n" +
                "    param:\n" +
                "      in: query\n" +
                "      description: parameter description\n" +
                "      summary: parameter summary\n" +
                "      schema:\n" +
                "        type: string\n" +
                "  examples:\n" +
                "    example:\n" +
                "      summary: example summary\n" +
                "      description: example description\n" +
                "      value: This is an example/\n" +
                "  requestBodies:\n" +
                "    body:\n" +
                "      content:\n" +
                "        application/json:\n" +
                "          schema: {}\n" +
                "  headers:\n" +
                "    test-head:\n" +
                "      description: test header description\n" +
                "      summary: test header summary\n" +
                "  securitySchemes:\n" +
                "    basic:\n" +
                "      description: ref security description\n" +
                "      summary: ref security summary\n" +
                "      in: header\n" +
                "      scheme: http\n" +
                "  links:\n" +
                "    Link:\n" +
                "      operationRef: \"#/paths/~12.0~1repositories~1{username}/get\"\n" +
                "  callbacks:\n" +
                "    TestCallback:\n" +
                "      '{$request.query.queryUrl}':\n" +
                "        description: test path item\n" +
                "        post:\n" +
                "          operationId: testPathItem\n" +
                "  pathItems:\n" +
                "    /pathTest:\n" +
                "      description: test path item\n" +
                "      get:\n" +
                "        operationId: testPathItem\n" +
                "        responses:\n" +
                "          \"200\":\n" +
                "            description: response description");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"components\" : {\n" +
                "    \"schemas\" : {\n" +
                "      \"stringTest\" : {\n" +
                "        \"type\" : \"string\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"responses\" : {\n" +
                "      \"201\" : {\n" +
                "        \"description\" : \"api response description\",\n" +
                "        \"summary\" : \"api response summary\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"parameters\" : {\n" +
                "      \"param\" : {\n" +
                "        \"in\" : \"query\",\n" +
                "        \"description\" : \"parameter description\",\n" +
                "        \"summary\" : \"parameter summary\",\n" +
                "        \"schema\" : {\n" +
                "          \"type\" : \"string\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"examples\" : {\n" +
                "      \"example\" : {\n" +
                "        \"summary\" : \"example summary\",\n" +
                "        \"description\" : \"example description\",\n" +
                "        \"value\" : \"This is an example/\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"requestBodies\" : {\n" +
                "      \"body\" : {\n" +
                "        \"content\" : {\n" +
                "          \"application/json\" : {\n" +
                "            \"schema\" : { }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"headers\" : {\n" +
                "      \"test-head\" : {\n" +
                "        \"description\" : \"test header description\",\n" +
                "        \"summary\" : \"test header summary\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"securitySchemes\" : {\n" +
                "      \"basic\" : {\n" +
                "        \"description\" : \"ref security description\",\n" +
                "        \"summary\" : \"ref security summary\",\n" +
                "        \"in\" : \"header\",\n" +
                "        \"scheme\" : \"http\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"links\" : {\n" +
                "      \"Link\" : {\n" +
                "        \"operationRef\" : \"#/paths/~12.0~1repositories~1{username}/get\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"callbacks\" : {\n" +
                "      \"TestCallback\" : {\n" +
                "        \"{$request.query.queryUrl}\" : {\n" +
                "          \"description\" : \"test path item\",\n" +
                "          \"post\" : {\n" +
                "            \"operationId\" : \"testPathItem\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"pathItems\" : {\n" +
                "      \"/pathTest\" : {\n" +
                "        \"description\" : \"test path item\",\n" +
                "        \"get\" : {\n" +
                "          \"operationId\" : \"testPathItem\",\n" +
                "          \"responses\" : {\n" +
                "            \"200\" : {\n" +
                "              \"description\" : \"response description\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        openAPI.openapi("3.0.3");
        SerializationMatchers.assertEqualsToYaml(openAPI, "openapi: 3.0.3\n" +
                "components:\n" +
                "  schemas:\n" +
                "    stringTest:\n" +
                "      type: string\n" +
                "  responses:\n" +
                "    \"201\":\n" +
                "      description: api response description\n" +
                "  parameters:\n" +
                "    param:\n" +
                "      in: query\n" +
                "      description: parameter description\n" +
                "      schema:\n" +
                "        type: string\n" +
                "  examples:\n" +
                "    example:\n" +
                "      summary: example summary\n" +
                "      description: example description\n" +
                "      value: This is an example/\n" +
                "  requestBodies:\n" +
                "    body:\n" +
                "      content:\n" +
                "        application/json:\n" +
                "          schema:\n" +
                "            type: object\n" +
                "  headers:\n" +
                "    test-head:\n" +
                "      description: test header description\n" +
                "  securitySchemes:\n" +
                "    basic:\n" +
                "      description: ref security description\n" +
                "      in: header\n" +
                "      scheme: http\n" +
                "  links:\n" +
                "    Link:\n" +
                "      operationRef: \"#/paths/~12.0~1repositories~1{username}/get\"\n" +
                "  callbacks:\n" +
                "    TestCallback:\n" +
                "      '{$request.query.queryUrl}':\n" +
                "        description: test path item\n" +
                "        post:\n" +
                "          operationId: testPathItem");

        SerializationMatchers.assertEqualsToJson(openAPI, "{\n" +
                "  \"openapi\" : \"3.0.3\",\n" +
                "  \"components\" : {\n" +
                "    \"schemas\" : {\n" +
                "      \"stringTest\" : {\n" +
                "        \"type\" : \"string\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"responses\" : {\n" +
                "      \"201\" : {\n" +
                "        \"description\" : \"api response description\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"parameters\" : {\n" +
                "      \"param\" : {\n" +
                "        \"in\" : \"query\",\n" +
                "        \"description\" : \"parameter description\",\n" +
                "        \"schema\" : {\n" +
                "          \"type\" : \"string\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"examples\" : {\n" +
                "      \"example\" : {\n" +
                "        \"summary\" : \"example summary\",\n" +
                "        \"description\" : \"example description\",\n" +
                "        \"value\" : \"This is an example/\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"requestBodies\" : {\n" +
                "      \"body\" : {\n" +
                "        \"content\" : {\n" +
                "          \"application/json\" : {\n" +
                "            \"schema\" : {\n" +
                "              \"type\" : \"object\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"headers\" : {\n" +
                "      \"test-head\" : {\n" +
                "        \"description\" : \"test header description\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"securitySchemes\" : {\n" +
                "      \"basic\" : {\n" +
                "        \"description\" : \"ref security description\",\n" +
                "        \"in\" : \"header\",\n" +
                "        \"scheme\" : \"http\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"links\" : {\n" +
                "      \"Link\" : {\n" +
                "        \"operationRef\" : \"#/paths/~12.0~1repositories~1{username}/get\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"callbacks\" : {\n" +
                "      \"TestCallback\" : {\n" +
                "        \"{$request.query.queryUrl}\" : {\n" +
                "          \"description\" : \"test path item\",\n" +
                "          \"post\" : {\n" +
                "            \"operationId\" : \"testPathItem\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testDiscriminatorSerialization() {
        Schema<String> propertySchema1 = new StringSchema();
        propertySchema1.addType(propertySchema1.getType());

        Schema<String> propertySchema2 = new StringSchema();
        propertySchema2.addType(propertySchema2.getType());

        Discriminator discriminator = new Discriminator().propertyName("type");
        discriminator.addExtension("x-otherName", "discriminationType");

        Schema schema = new ObjectSchema()
                .addProperties("name", propertySchema1)
                .addProperties("type", propertySchema1)
                .discriminator(discriminator);

        schema.addType(schema.getType());
        OpenAPI openAPI = new OpenAPI().openapi("3.1.0").components(new Components()
                .addSchemas("pet", schema));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "components:\n" +
                "  schemas:\n" +
                "    pet:\n" +
                "      properties:\n" +
                "        name:\n" +
                "          type: string\n" +
                "        type:\n" +
                "          type: string\n" +
                "      discriminator:\n" +
                "        propertyName: type\n" +
                "        x-otherName: discriminationType\n" +
                "      type: object");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"components\" : {\n" +
                "    \"schemas\" : {\n" +
                "      \"pet\" : {\n" +
                "        \"properties\" : {\n" +
                "          \"name\" : {\n" +
                "            \"type\" : \"string\"\n" +
                "          },\n" +
                "          \"type\" : {\n" +
                "            \"type\" : \"string\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"discriminator\" : {\n" +
                "          \"propertyName\" : \"type\",\n" +
                "          \"x-otherName\" : \"discriminationType\"\n" +
                "        },\n" +
                "        \"type\" : \"object\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        openAPI.openapi("3.0.3");

        SerializationMatchers.assertEqualsToYaml(openAPI, "openapi: 3.0.3\n" +
                "components:\n" +
                "  schemas:\n" +
                "    pet:\n" +
                "      properties:\n" +
                "        name:\n" +
                "          type: string\n" +
                "        type:\n" +
                "          type: string\n" +
                "      discriminator:\n" +
                "        propertyName: type\n" +
                "      type: object");

        SerializationMatchers.assertEqualsToJson(openAPI, "{\n" +
                "  \"openapi\" : \"3.0.3\",\n" +
                "  \"components\" : {\n" +
                "    \"schemas\" : {\n" +
                "      \"pet\" : {\n" +
                "        \"properties\" : {\n" +
                "          \"name\" : {\n" +
                "            \"type\" : \"string\"\n" +
                "          },\n" +
                "          \"type\" : {\n" +
                "            \"type\" : \"string\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"discriminator\" : {\n" +
                "          \"propertyName\" : \"type\"\n" +
                "        },\n" +
                "        \"type\" : \"object\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testPathItemsRefSerialization() {
        OpenAPI openAPI = new OpenAPI().openapi("3.1.0")
                .path("/pathTest", new PathItem()
                        .$ref("#/components/pathItems/pathTest")
                        .description("This is a ref path item")
                        .summary("ref path item")
                        .addParametersItem(new Parameter().in("query").schema(new Schema().type("string"))) //should be ignored
                )
                .components(new Components()
                        .addPathItems("pathTest", new PathItem()
                                .description("test path item")
                                .get(new Operation()
                                        .operationId("testPathItem")
                                        .responses(new ApiResponses()
                                                .addApiResponse("200", new ApiResponse().description("response description"))))));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "paths:\n" +
                "  /pathTest:\n" +
                "    $ref: '#/components/pathItems/pathTest'\n" +
                "    description: This is a ref path item\n" +
                "    summary: ref path item\n" +
                "components:\n" +
                "  pathItems:\n" +
                "    pathTest:\n" +
                "      description: test path item\n" +
                "      get:\n" +
                "        operationId: testPathItem\n" +
                "        responses:\n" +
                "          \"200\":\n" +
                "            description: response description");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"paths\" : {\n" +
                "    \"/pathTest\" : {\n" +
                "      \"summary\" : \"ref path item\",\n" +
                "      \"description\" : \"This is a ref path item\",\n" +
                "      \"$ref\" : \"#/components/pathItems/pathTest\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"pathItems\" : {\n" +
                "      \"pathTest\" : {\n" +
                "        \"description\" : \"test path item\",\n" +
                "        \"get\" : {\n" +
                "          \"operationId\" : \"testPathItem\",\n" +
                "          \"responses\" : {\n" +
                "            \"200\" : {\n" +
                "              \"description\" : \"response description\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testPathItemsInCallbackRefSerialization() {
        OpenAPI openAPI = new OpenAPI().openapi("3.1.0")
                .components(new Components()
                        .addCallbacks("aCallback", new Callback()
                                .description("a callback description")
                                .summary("a callback summary")
                                .addPathItem("'{$request.query.queryUrl}'", new PathItem()
                                        .$ref("#/components/pathItems/pathTest")
                                        .description("path item in callback description")
                                        .summary("path item in callback summary")
                                        .addParametersItem(new Parameter().in("query").schema(new Schema().type("string"))) //should be ignored
                                ))
                        .addPathItems("pathTest", new PathItem()
                                .description("test path item")
                                .get(new Operation()
                                        .operationId("testPathItem")
                                        .responses(new ApiResponses()
                                                .addApiResponse("200", new ApiResponse().description("response description"))))));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "components:\n" +
                "  callbacks:\n" +
                "    aCallback:\n" +
                "      '''{$request.query.queryUrl}''':\n" +
                "        $ref: '#/components/pathItems/pathTest'\n" +
                "        description: path item in callback description\n" +
                "        summary: path item in callback summary\n" +
                "  pathItems:\n" +
                "    pathTest:\n" +
                "      description: test path item\n" +
                "      get:\n" +
                "        operationId: testPathItem\n" +
                "        responses:\n" +
                "          \"200\":\n" +
                "            description: response description");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"components\" : {\n" +
                "    \"callbacks\" : {\n" +
                "      \"aCallback\" : {\n" +
                "        \"'{$request.query.queryUrl}'\" : {\n" +
                "          \"$ref\" : \"#/components/pathItems/pathTest\",\n" +
                "          \"description\" : \"path item in callback description\",\n" +
                "          \"summary\" : \"path item in callback summary\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"pathItems\" : {\n" +
                "      \"pathTest\" : {\n" +
                "        \"description\" : \"test path item\",\n" +
                "        \"get\" : {\n" +
                "          \"operationId\" : \"testPathItem\",\n" +
                "          \"responses\" : {\n" +
                "            \"200\" : {\n" +
                "              \"description\" : \"response description\"\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        openAPI.openapi("3.0.0");
        SerializationMatchers.assertEqualsToYaml(openAPI, "openapi: 3.0.0\n" +
                "components:\n" +
                "  callbacks:\n" +
                "    aCallback:\n" +
                "      '''{$request.query.queryUrl}''':\n" +
                "        $ref: '#/components/pathItems/pathTest'");
        SerializationMatchers.assertEqualsToJson(openAPI, "{\n" +
                "  \"openapi\" : \"3.0.0\",\n" +
                "  \"components\" : {\n" +
                "    \"callbacks\" : {\n" +
                "      \"aCallback\" : {\n" +
                "        \"'{$request.query.queryUrl}'\" : {\n" +
                "          \"$ref\" : \"#/components/pathItems/pathTest\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testResponseRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .path("/test", new PathItem()
                        .description("test path item")
                        .get(new Operation()
                                .operationId("testPathItem")
                                .responses(new ApiResponses()
                                        .addApiResponse("200"   , new ApiResponse()
                                                .description("point to a $ref response")
                                                .summary("point to a $ref response")
                                                .addHeaderObject("header", new Header().description("header")) // should be ignored
                                                .$ref("#/components/responses/okResponse")))))
                .components(new Components()
                        .addResponses("okResponse", new ApiResponse().description("everything is good")));
        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "paths:\n" +
                "  /test:\n" +
                "    description: test path item\n" +
                "    get:\n" +
                "      operationId: testPathItem\n" +
                "      responses:\n" +
                "        \"200\":\n" +
                "          description: point to a $ref response\n" +
                "          summary: point to a $ref response\n" +
                "          $ref: '#/components/responses/okResponse'\n" +
                "components:\n" +
                "  responses:\n" +
                "    okResponse:\n" +
                "      description: everything is good");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"paths\" : {\n" +
                "    \"/test\" : {\n" +
                "      \"description\" : \"test path item\",\n" +
                "      \"get\" : {\n" +
                "        \"operationId\" : \"testPathItem\",\n" +
                "        \"responses\" : {\n" +
                "          \"200\" : {\n" +
                "            \"description\" : \"point to a $ref response\",\n" +
                "            \"summary\" : \"point to a $ref response\",\n" +
                "            \"$ref\" : \"#/components/responses/okResponse\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"responses\" : {\n" +
                "      \"okResponse\" : {\n" +
                "        \"description\" : \"everything is good\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testParameterRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .components(new Components()
                        .addParameters("testParameter", new Parameter()
                                .in("query")
                                .name("param0")
                                .schema(new Schema().type("string")))
                        .addParameters("testParameter2", new Parameter()
                                .in("query")
                                .name("param2")
                                .schema(new Schema().type("string")))
                )
                .path("/test", new
                        PathItem()
                        .description("test path item")
                        .addParametersItem(new Parameter()
                                .name("globalParam")
                                .in("query")
                                .description("global parameter")
                                .summary("test param")
                                .schema(new Schema().type("string")))
                        .addParametersItem(new Parameter()
                                .description("global parameter")
                                .summary("test param")
                                .name("ignoredName") // should be ignored
                                .$ref("#/components/parameters/testParameter2"))
                        .get(new Operation()
                                .operationId("testPathItem")
                                .addParametersItem(new Parameter()
                                        .$ref("#/components/parameters/testParameter")
                                        .name("testParam") // should be ignored
                                        .description("test parameter")
                                        .summary("test param"))
                                .addParametersItem(new Parameter()
                                        .name("testParam") // should be ignored
                                        .in("query")
                                        .description("test parameter")
                                        .summary("test param")
                                        .schema(new Schema().type("string")))));
        Json31.prettyPrint(openAPI);

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "paths:\n" +
                "  /test:\n" +
                "    description: test path item\n" +
                "    get:\n" +
                "      operationId: testPathItem\n" +
                "      parameters:\n" +
                "      - $ref: '#/components/parameters/testParameter'\n" +
                "        description: test parameter\n" +
                "        summary: test param\n" +
                "      - name: testParam\n" +
                "        in: query\n" +
                "        description: test parameter\n" +
                "        summary: test param\n" +
                "        schema: {}\n" +
                "    parameters:\n" +
                "    - name: globalParam\n" +
                "      in: query\n" +
                "      description: global parameter\n" +
                "      summary: test param\n" +
                "      schema: {}\n" +
                "    - $ref: '#/components/parameters/testParameter2'\n" +
                "      description: global parameter\n" +
                "      summary: test param\n" +
                "components:\n" +
                "  parameters:\n" +
                "    testParameter:\n" +
                "      name: param0\n" +
                "      in: query\n" +
                "      schema: {}\n" +
                "    testParameter2:\n" +
                "      name: param2\n" +
                "      in: query\n" +
                "      schema: {}");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"paths\" : {\n" +
                "    \"/test\" : {\n" +
                "      \"description\" : \"test path item\",\n" +
                "      \"get\" : {\n" +
                "        \"operationId\" : \"testPathItem\",\n" +
                "        \"parameters\" : [ {\n" +
                "          \"$ref\" : \"#/components/parameters/testParameter\",\n" +
                "          \"description\" : \"test parameter\",\n" +
                "          \"summary\" : \"test param\"\n" +
                "        }, {\n" +
                "          \"name\" : \"testParam\",\n" +
                "          \"in\" : \"query\",\n" +
                "          \"description\" : \"test parameter\",\n" +
                "          \"summary\" : \"test param\",\n" +
                "          \"schema\" : { }\n" +
                "        } ]\n" +
                "      },\n" +
                "      \"parameters\" : [ {\n" +
                "        \"name\" : \"globalParam\",\n" +
                "        \"in\" : \"query\",\n" +
                "        \"description\" : \"global parameter\",\n" +
                "        \"summary\" : \"test param\",\n" +
                "        \"schema\" : { }\n" +
                "      }, {\n" +
                "        \"$ref\" : \"#/components/parameters/testParameter2\",\n" +
                "        \"description\" : \"global parameter\",\n" +
                "        \"summary\" : \"test param\"\n" +
                "      } ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"parameters\" : {\n" +
                "      \"testParameter\" : {\n" +
                "        \"name\" : \"param0\",\n" +
                "        \"in\" : \"query\",\n" +
                "        \"schema\" : { }\n" +
                "      },\n" +
                "      \"testParameter2\" : {\n" +
                "        \"name\" : \"param2\",\n" +
                "        \"in\" : \"query\",\n" +
                "        \"schema\" : { }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testExampleRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .components(new Components()
                        .addExamples("testExample", new Example()
                                .value("Example on test")
                                .description("this is a example desc")
                                .summary("this is a summary test"))
                        .addSchemas("schema", new Schema().example(new Example()
                                .$ref("#/components/examples/testExample")
                                .description("ref description")
                                .summary("ref summary"))));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "components:\n" +
                "  schemas:\n" +
                "    schema:\n" +
                "      example:\n" +
                "        summary: ref summary\n" +
                "        description: ref description\n" +
                "        $ref: '#/components/examples/testExample'\n" +
                "  examples:\n" +
                "    testExample:\n" +
                "      summary: this is a summary test\n" +
                "      description: this is a example desc\n" +
                "      value: Example on test");
        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"components\" : {\n" +
                "    \"schemas\" : {\n" +
                "      \"schema\" : {\n" +
                "        \"example\" : {\n" +
                "          \"summary\" : \"ref summary\",\n" +
                "          \"description\" : \"ref description\",\n" +
                "          \"$ref\" : \"#/components/examples/testExample\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"examples\" : {\n" +
                "      \"testExample\" : {\n" +
                "        \"summary\" : \"this is a summary test\",\n" +
                "        \"description\" : \"this is a example desc\",\n" +
                "        \"value\" : \"Example on test\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }
    @Test
    public void testRequestBodyRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .path("/test", new PathItem()
                        .description("test path item")
                        .post(new Operation()
                                .operationId("testPathItem")
                                .requestBody(new RequestBody()
                                        .$ref("#/components/requestBodies/body")
                                        .required(true) //it should be ignored
                                        .description("ref request body")
                                        .summary("ref req body"))))
                .components(new Components()
                        .addRequestBodies("body", new RequestBody()
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new ObjectSchema())))))
                ;
        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "paths:\n" +
                "  /test:\n" +
                "    description: test path item\n" +
                "    post:\n" +
                "      operationId: testPathItem\n" +
                "      requestBody:\n" +
                "        description: ref request body\n" +
                "        summary: ref req body\n" +
                "        $ref: '#/components/requestBodies/body'\n" +
                "components:\n" +
                "  requestBodies:\n" +
                "    body:\n" +
                "      content:\n" +
                "        application/json:\n" +
                "          schema: {}");
        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"paths\" : {\n" +
                "    \"/test\" : {\n" +
                "      \"description\" : \"test path item\",\n" +
                "      \"post\" : {\n" +
                "        \"operationId\" : \"testPathItem\",\n" +
                "        \"requestBody\" : {\n" +
                "          \"description\" : \"ref request body\",\n" +
                "          \"summary\" : \"ref req body\",\n" +
                "          \"$ref\" : \"#/components/requestBodies/body\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"requestBodies\" : {\n" +
                "      \"body\" : {\n" +
                "        \"content\" : {\n" +
                "          \"application/json\" : {\n" +
                "            \"schema\" : { }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testHeaderRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .path("/test", new PathItem()
                        .description("test path item")
                        .post(new Operation()
                                .operationId("testPathItem")
                                .responses(new ApiResponses()
                                        .addApiResponse("default", new ApiResponse()
                                                .addLink("responseLink", new Link().addHeaderObject("link-header", new Header()
                                                        .$ref("#/components/responses/okResponse")
                                                        .content(new Content().addMediaType("application/json", new MediaType())) // should be ignored
                                                        .description("ref header description")
                                                        .summary("ref header summary")))
                                                .description("default response")
                                                .addHeaderObject("header", new Header()
                                                        .$ref("#/components/responses/response-header")
                                                        .content(new Content().addMediaType("application/json", new MediaType())) // should be ignored
                                                        .description("ref header description")
                                                        .summary("ref header summary"))))
                        ))
                .components(new Components()
                        .addHeaders("response-header", new Header()
                                .content(new Content().addMediaType("application/json", new MediaType()))
                                .schema(new Schema().type("string"))
                                .description("test response header description")
                                .summary("test response header summary")));
        Json31.prettyPrint(openAPI);

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "paths:\n" +
                "  /test:\n" +
                "    description: test path item\n" +
                "    post:\n" +
                "      operationId: testPathItem\n" +
                "      responses:\n" +
                "        default:\n" +
                "          description: default response\n" +
                "          headers:\n" +
                "            header:\n" +
                "              $ref: '#/components/responses/response-header'\n" +
                "              description: ref header description\n" +
                "              summary: ref header summary\n" +
                "          links:\n" +
                "            responseLink:\n" +
                "              headers:\n" +
                "                link-header:\n" +
                "                  $ref: '#/components/responses/okResponse'\n" +
                "                  description: ref header description\n" +
                "                  summary: ref header summary\n" +
                "components:\n" +
                "  headers:\n" +
                "    response-header:\n" +
                "      description: test response header description\n" +
                "      summary: test response header summary\n" +
                "      schema: {}\n" +
                "      content:\n" +
                "        application/json: {}");
        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"paths\" : {\n" +
                "    \"/test\" : {\n" +
                "      \"description\" : \"test path item\",\n" +
                "      \"post\" : {\n" +
                "        \"operationId\" : \"testPathItem\",\n" +
                "        \"responses\" : {\n" +
                "          \"default\" : {\n" +
                "            \"description\" : \"default response\",\n" +
                "            \"headers\" : {\n" +
                "              \"header\" : {\n" +
                "                \"$ref\" : \"#/components/responses/response-header\",\n" +
                "                \"description\" : \"ref header description\",\n" +
                "                \"summary\" : \"ref header summary\"\n" +
                "              }\n" +
                "            },\n" +
                "            \"links\" : {\n" +
                "              \"responseLink\" : {\n" +
                "                \"headers\" : {\n" +
                "                  \"link-header\" : {\n" +
                "                    \"$ref\" : \"#/components/responses/okResponse\",\n" +
                "                    \"description\" : \"ref header description\",\n" +
                "                    \"summary\" : \"ref header summary\"\n" +
                "                  }\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"headers\" : {\n" +
                "      \"response-header\" : {\n" +
                "        \"description\" : \"test response header description\",\n" +
                "        \"summary\" : \"test response header summary\",\n" +
                "        \"schema\" : { },\n" +
                "        \"content\" : {\n" +
                "          \"application/json\" : { }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testSecuritySchemeRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .components(new Components().addSecuritySchemes("basic", new SecurityScheme()
                        .$ref("https://external.site.com/#components/securitySchemes/basic")
                        .description("ref security description")
                        .summary("ref security summary")));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "components:\n" +
                "  securitySchemes:\n" +
                "    basic:\n" +
                "      description: ref security description\n" +
                "      summary: ref security summary\n" +
                "      $ref: https://external.site.com/#components/securitySchemes/basic");
        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"components\" : {\n" +
                "    \"securitySchemes\" : {\n" +
                "      \"basic\" : {\n" +
                "        \"description\" : \"ref security description\",\n" +
                "        \"summary\" : \"ref security summary\",\n" +
                "        \"$ref\" : \"https://external.site.com/#components/securitySchemes/basic\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testLinkRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .path("/test", new PathItem()
                        .description("test path item")
                        .post(new Operation()
                                .operationId("testPathItem")
                                .responses(new ApiResponses()
                                        .addApiResponse("default", new ApiResponse()
                                                .description("default response")
                                                .addLink("link", new Link()
                                                        .$ref("#/components/links/Link")
                                                        .description("ref link description")
                                                        .summary("ref link summary"))))))
                .components(new Components().addLinks("Link", new Link()
                        .operationRef("#/paths/~12.0~1repositories~1{username}/get")));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "paths:\n" +
                "  /test:\n" +
                "    description: test path item\n" +
                "    post:\n" +
                "      operationId: testPathItem\n" +
                "      responses:\n" +
                "        default:\n" +
                "          description: default response\n" +
                "          links:\n" +
                "            link:\n" +
                "              description: ref link description\n" +
                "              summary: ref link summary\n" +
                "              $ref: '#/components/links/Link'\n" +
                "components:\n" +
                "  links:\n" +
                "    Link:\n" +
                "      operationRef: \"#/paths/~12.0~1repositories~1{username}/get\"");
        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"paths\" : {\n" +
                "    \"/test\" : {\n" +
                "      \"description\" : \"test path item\",\n" +
                "      \"post\" : {\n" +
                "        \"operationId\" : \"testPathItem\",\n" +
                "        \"responses\" : {\n" +
                "          \"default\" : {\n" +
                "            \"description\" : \"default response\",\n" +
                "            \"links\" : {\n" +
                "              \"link\" : {\n" +
                "                \"description\" : \"ref link description\",\n" +
                "                \"summary\" : \"ref link summary\",\n" +
                "                \"$ref\" : \"#/components/links/Link\"\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"links\" : {\n" +
                "      \"Link\" : {\n" +
                "        \"operationRef\" : \"#/paths/~12.0~1repositories~1{username}/get\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }

    @Test
    public void testCallRefSerialization() {
        OpenAPI openAPI = new OpenAPI()
                .openapi("3.1.0")
                .path("/test", new PathItem()
                        .description("test path item")
                        .post(new Operation()
                                .operationId("testPathItem")
                                .addCallbacks("callbackSample", new Callback()
                                        .$ref("#/components/callbacks/TestCallback")
                                        .description("ref callback description")
                                        .summary("ref callback summary"))))
                .components(new Components().addCallbacks("TestCallback", new Callback().addPathItem("{$request.query.queryUrl}", new PathItem()
                        .description("test path item")
                        .post(new Operation()
                                .operationId("testPathItem")))));

        SerializationMatchers.assertEqualsToYaml31(openAPI, "openapi: 3.1.0\n" +
                "paths:\n" +
                "  /test:\n" +
                "    description: test path item\n" +
                "    post:\n" +
                "      operationId: testPathItem\n" +
                "      callbacks:\n" +
                "        callbackSample:\n" +
                "          $ref: '#/components/callbacks/TestCallback'\n" +
                "          description: ref callback description\n" +
                "          summary: ref callback summary\n" +
                "components:\n" +
                "  callbacks:\n" +
                "    TestCallback:\n" +
                "      '{$request.query.queryUrl}':\n" +
                "        description: test path item\n" +
                "        post:\n" +
                "          operationId: testPathItem");

        SerializationMatchers.assertEqualsToJson31(openAPI, "{\n" +
                "  \"openapi\" : \"3.1.0\",\n" +
                "  \"paths\" : {\n" +
                "    \"/test\" : {\n" +
                "      \"description\" : \"test path item\",\n" +
                "      \"post\" : {\n" +
                "        \"operationId\" : \"testPathItem\",\n" +
                "        \"callbacks\" : {\n" +
                "          \"callbackSample\" : {\n" +
                "            \"$ref\" : \"#/components/callbacks/TestCallback\",\n" +
                "            \"description\" : \"ref callback description\",\n" +
                "            \"summary\" : \"ref callback summary\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"components\" : {\n" +
                "    \"callbacks\" : {\n" +
                "      \"TestCallback\" : {\n" +
                "        \"{$request.query.queryUrl}\" : {\n" +
                "          \"description\" : \"test path item\",\n" +
                "          \"post\" : {\n" +
                "            \"operationId\" : \"testPathItem\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");
    }


}
