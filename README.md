# Detailed demo of REST API Validations with Spring Boot
## Where to use?
#### In the Controller to validate:
- Headers
- Path Parameters
- Query Parameters
- Request Body
## How to use Spring Boot Starter Validation?
#### Add **spring-boot-starter-validation** to your pom.xml dependency list
```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
  </dependency>
```
#### Commonly used constraint annotations from the package **jakarta.validation.constraints.*** of **spring-boot-starter-validation**
| 🏷️ Annotation               | 📋 What It Validates | 🔧 Example Usage|
|------------------------------|----------------------|-----------------|
| @NotNull                     | Field must not be null | @NotNull private String name;|
| @NotEmpty                    | String/Collection must not be null or empty | @NotEmpty private List<String> items;|
| @NotBlank                    | String must not be null and must have non-whitespace text | @NotBlank private String email;|
| @Size(min, max)              | Size of String/Collection/Array | @Size(min = 3, max = 20) private String username;|
| @Min / @Max                  | Minimum / Maximum numeric value | @Min(18) private int age;|
| @Positive / @PositiveOrZero  | Number must be positive / positive or zero | @Positive private int quantity;|
| @Negative / @NegativeOrZero  | Number must be negative / negative or zero | @Negative private int balance;|
| @Pattern                     | Must match a regex pattern | @Pattern(regexp = "\\d{10}") private String phone;|
| @Email                       | Must be a valid email format | @Email private String email;|
| @Future / @FutureOrPresent   | Date must be in the future / future or present | @Future private LocalDate eventDate;|
| @Past / @PastOrPresent       | Date must be in the past / past or present | @Past private LocalDate birthDate;|
| @AssertTrue / @AssertFalse   | Boolean field must be true / false | @AssertTrue private boolean agreedToTerms;|
| @Digits(integer, fraction)   | Validates number of integer/fraction digits | @Digits(integer=6, fraction=2) private BigDecimal salary;|
| @DecimalMin / @DecimalMax    | Minimum/maximum for decimal numbers (BigDecimal, etc.) | @DecimalMin("0.01") private BigDecimal price;|
| @CreditCardNumber (Hibernate) | Validates credit card numbers using the Luhn algorithm | @CreditCardNumber private String ccNumber;|
| @URL (Hibernate)             | Validates a valid URL format | @URL private String website;|

## For Path Parameters and Query Parameters
- In your SpringBoot Controller add **@Validated** annotation to the class definition.
  org.springframework.validation.annotation.Validated
```java
    @RequestMapping(value = "/user", produces = {"application/json"})
    @RestController
    @Validated //For query/path params @Validated is used here
    public class UserAccountController {}
```
- Add constraint annotation to the method arguments keeping the method arguments optional. E.g.
```java
    @GetMapping("/{id}") // For query/path params use @Validated for controller
    public void getInfo(@NotNull @Size(min=3, max = 5, message = "Id must have a length of at least 3 and at most 5") @PathVariable(required = false) String id,
        @NotNull @Size(min=2, message = "Activity must have a length of at least 2") @RequestParam(required = false) String activity) {/*Execute API here*/}
    // Use required = false  with @NotNull annotation
```
- Check [controller](src/main/java/com/springvalidation/demo/controller/UserAccountController.java)
- Exception Handling. Catching and processing exceptions to prevent crashes and return proper responses. Add the following to your Controller Advice.
```java
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorListView onConstraintViolationException(ConstraintViolationException e) {
        ValidationErrorListView error = new ValidationErrorListView();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            error.getViolations().add(new ValidationErrorView(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        }
        return error;
    }
```
- Check [controller advice](src/main/java/com/springvalidation/demo/controller/advice/ErrorHandlingControllerAdvice.java)

## For Request Body
- Add @Valid annotation(from package jakarta.validation) to the method argument. E.g.
```java
    @PostMapping("/add-all-info") // @Valid annotation is used to validate the method argument() and nested attributes
    public void addAllInfo(@Valid @RequestBody UserAccountDto1 userAccountDto2) {}
```
- Add constraint annotations to attributes and nested attributes.
```java
public class UserAccountDto1 {

    @NotNull
    @Size(min = 4, max = 15)
    private String password;

    @NotBlank
    private String username;

    @Min(value = 18, message = "Age should not be less than 18")
    private int age;

    private List<@Valid ProductDto> ownedProducts;

    private Map<String,@Valid ProductDto> ownedProductsMap;

    @Pattern(regexp = "^ACCOUNT-")
    private String accountId;

    @DateOfBirth
    private String dateOfBirth;

    @NotNull
    @Min(1)
    private Integer returnedProductsCount;
}

@Getter
@Setter
public class ProductDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @Positive
    private Long quantity;
}
```
- Notice how @Valid annotation is added in the List and Map attributes.
- Check [controller](src/main/java/com/springvalidation/demo/controller/UserAccountController.java) , [entity1](src/main/java/com/springvalidation/demo/dto/UserAccountDto1.java) and [entity2](src/main/java/com/springvalidation/demo/dto/ProductDto.java)
- Exception Handling. Catching and processing exceptions to prevent crashes and return proper responses. Add the following to your Controller Advice.
```java
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorListView onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorListView error = new ValidationErrorListView();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(new ValidationErrorView(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }
```
- Check [controller advice](src/main/java/com/springvalidation/demo/controller/advice/ErrorHandlingControllerAdvice.java)
## This is how the validation error response looks like
```json
400 Bad Request
{
    "message": "Some fields are not valid",
    "violations": [
        {
            "fieldName": "returnedProductsCount",
            "message": "must not be null"
        },
        {
            "fieldName": "username",
            "message": "must not be blank"
        },
        {
            "fieldName": "ownedProducts[0].quantity",
            "message": "must be greater than 0"
        },
        {
            "fieldName": "password",
            "message": "size must be between 4 and 15"
        },
        {
            "fieldName": "ownedProducts[0].name",
            "message": "must not be blank"
        },
        {
            "fieldName": "ownedProductsMap[1].quantity",
            "message": "must be greater than 0"
        },
        {
            "fieldName": "ownedProductsMap[1].name",
            "message": "must not be blank"
        },
        {
            "fieldName": "dateOfBirth",
            "message": "Invalid Date Of Birth. Expected MM/dd/yyyy"
        },
        {
            "fieldName": "age",
            "message": "Age should not be less than 18"
        },
        {
            "fieldName": "ownedProducts[1].name",
            "message": "must not be blank"
        }
    ]
}
```
## To add Custom Constraint
- Create a constraint class
```java
@Documented
@Constraint(validatedBy = DateOfBirthValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfBirth {
    String message() default "Invalid Date Of Birth. Expected MM/dd/yyyy";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```
- Create a validator class
```java
@Slf4j
public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, String> {

  @Override
  public boolean isValid(String dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
    if(dateOfBirth==null){
      return Boolean.FALSE;
    }
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    simpleDateFormat.setLenient(Boolean.FALSE);
    try
    {
      simpleDateFormat.parse(dateOfBirth);
      return Boolean.TRUE;
    }
    /* Date format is invalid */
    catch (ParseException e)
    {
      log.error("Some error message");
      return Boolean.FALSE;
    }
  }
}
```
- Use the custom constraint
```java
    @DateOfBirth
    private String dateOfBirth;
```
- Check [constraint class](src/main/java/com/springvalidation/demo/validation/constraints/DateOfBirth.java) and [validator class](src/main/java/com/springvalidation/demo/validation/constraints/validator/DateOfBirthValidator.java)
## Partial Validation using groups
- You should avoid this as it's an antipattern and conflicts separation of concerns
- Declare interface for validation group. E.g.
```java
public interface BasicInfo {}
```
- Attach Constraints to only specific groups. E.g.
```java
@Getter
@Setter
public class UserAccountDto2 {

  @NotNull(groups = BasicInfo.class)
  @Size(min = 4, max = 15, groups = BasicInfo.class)
  private String password;

  @NotBlank(groups = BasicInfo.class)
  private String username;

  @Min(value = 18, message = "Age should not be less than 18", groups = AdvanceInfo.class)
  private int age;
}
```
- Use @Validated(\<Group\>.class) with method argument
```java
    @PostMapping("/add-basic-info")
    /*@Validated annotation is used here if we want granular validation
    only for fields that belong to a particular group. Here BasicInfo group is used.
    In UserAccountDto username and password have Constraints belonging to group BasicInfo.
    */
    public void addBasicInfo(@Validated(BasicInfo.class) @RequestBody UserAccountDto2 userAccountDto2) {}
```
- Only the constraints that belong to that particular group will be applied. Other constraints won't be applied.