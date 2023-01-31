package utility;

public class Const {

    private  static final String ACCESS_TOKEN = "";
    private  static final String Auth= "Authorization";
    private  static final String AppType= "application/json";
    private  static final String APIParameter= "";
    private static final String sauceURL = "";
    private  static final String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\";




    public static String getSauceURL() {
        return sauceURL;
    }

    public static String getAPIParameter() {
        return APIParameter;
    }

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static String getAuth() {
        return Auth;
    }

    public static String getAppType() {
        return AppType;
    }
    public static final String getOdsQuery(String payRollID){
        return "SELECT\n" +
                "Distinct V.[TransactionDetailId],\n" +
                "H.[SourceEmployeeID],\n" +
                "H.[GPEmployeeID],\n" +
                "H.[GPEmployeeCompany],\n" +
                "H.[AdmissionServiceOfficeNumber],\n" +
                "H.[CreditingServiceOfficeNumber],\n" +
                "H.[ManagedCareOfficeReferral],\n" +
                "H.[TransactionDate],\n" +
                "H.[SourceSystem],\n" +
                "H.[SourceVisitID],\n" +
                "H.[SourceLocalTaxCode],\n" +
                "PYR.[Code] as [PayorType],\n" +
                "H.[PayorName],\n" +
                "H.[SourcePayorID],\n" +
                "H.[ReferenceSourceVisitID],\n" +
                "CSM.[GPClientServiceManagerID],\n" +
                "CSM.[SourceClientServiceManagerID],\n" +
                "CSM.[ClientServiceManagerType],\n" +
                "D.[Duration],\n" +
                "D.[NumberOfUnits],\n" +
                "D.[Rate],\n" +
                "D.[Amount],\n" +
                "D.[StandardRate],\n" +
                "D.[StandardAmount],\n" +
                "TC.[Code] as [TransactionCategory],\n" +
                "TCL.[Code] as [TransactionClass],\n" +
                "ST.[Description] as [Service Type],\n" +
                "PT.[Code] as [PayType],\n" +
                "D.[ServiceLevelCode],\n" +
                "D.[PrimaryLevelCode],\n" +
                "D.[GPSpecialtyCode],\n" +
                "D.[PostDate],\n" +
                "DT.[Code] as [DayType],\n" +
                "D.[SourceBatchID],\n" +
                "D.[PayPeriod],\n" +
                "B.[MultiplierType],\n" +
                "B.[MultiplierFactor],\n" +
                "V.[ArriveTime],\n" +
                "V.[LeaveTime],\n" +
                "V.[SourceVisitId],\n" +
                "C.[SourceClientId],\n" +
                "C.[ClientFirstName],\n" +
                "C.[ClientMiddleInitial],\n" +
                "C.[ClientLastName],\n" +
                "C.[ClientAddress1],\n" +
                "C.[ClientAddress2],\n" +
                "C.[ClientCity],\n" +
                "C.[ClientState],\n" +
                "C.[ClientZipCode]\n" +
                "FROM [ODSDB].[dbo].[TransactionHeader] H\n" +
                "LEFT JOIN [ODSDB].[dbo].[TransactionDetail] D\n" +
                "ON H.TransactionHeaderID = D.TransactionHeaderID\n" +
                "LEFT JOIN [ODSDB].[dbo].[TransactionBenefitMultiplier] B\n" +
                "ON D.TransactionDetailID = B.TransactionDetailID\n" +
                "LEFT JOIN [ODSDB].[dbo].[vwODSTransactions] V\n" +
                "ON H.SourceVisitID = V.SourceVisitID\n" +
                "LEFT JOIN [ODSDB].[dbo].[TransactionCategory] TC\n" +
                "ON D.TransactionCategoryID = TC.TransactionCategoryID\n" +
                "LEFT JOIN [ODSDB].[dbo].[TransactionClass] TCL\n" +
                "ON D.TransactionClassID = TCL.TransactionClassID\n" +
                "LEFT JOIN [ODSDB].[dbo].[ServiceType] ST\n" +
                "ON D.ServiceTypeID = ST.ServiceTypeID\n" +
                "LEFT JOIN [ODSDB].[dbo].[PayType] PT\n" +
                "ON D.PayTypeID = PT.PayTypeID\n" +
                "LEFT JOIN [ODSDB].[dbo].[DayType] DT\n" +
                "ON D.DayTypeID = DT.DayTypeID\n" +
                "LEFT JOIN [ODSDB].[dbo].[PayorType] PYR\n" +
                "ON H.PayorTypeID = PYR.PayorTypeID\n" +
                "LEFT JOIN [ODSDB].[dbo].[VisitDetail] C\n" +
                "ON H.TransactionHeaderID = C.TransactionHeaderID\n" +
                "LEFT JOIN [ODSDB].[dbo].[TransactionClientServiceManager] CSM\n" +
                "ON H.TransactionHeaderID = CSM.TransactionHeaderID\n" +
                "where H.SourceVisitID = '"+ payRollID +"' ORDER BY V.TransactionDetailId DESC";
    }

    public static String getExcelPath(String fileName) {
        return filePath+fileName;
    }

}
