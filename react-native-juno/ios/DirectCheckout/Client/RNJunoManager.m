 #import "RNJunoManager.h"
 #import "RCTLog.h"
 #import "DirectCheckout-Swift.h"

 @implementation RNJunoManager

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(initializeJuno:(NSString *)token production:(BOOL *)production)
{
    RNJuno *rn = [RNJuno new];
    
    if(production){
         //Executado se Produção
        [rn initializeJunoWithToken:token production: true];
      RCTLogInfo(@"Pretending to create an event %@ at %@", token, @"TRUE");
    }else{
        
        //Executado se Homologação
        [rn initializeJunoWithToken:token production: false];
       RCTLogInfo(@"Pretending to create an event %@ at %@", token, @"FALSE");
        //DirectCheckout.initialize(publicToken: token, environment: .sandbox);
    }
}

RCT_EXPORT_METHOD(generateHash: (NSString *)number :(NSString *)name :(NSString *)ccv :(NSString *)month :(NSString *)year: (RCTResponseSenderBlock)callback)
{
    RNJuno *rn = [RNJuno new];
    //[rn generateHashWithCardNumber:number holderName:name securityCode:ccv expirationMonth:month expirationYear:year];
    
    RCTLogInfo(@"%@", number);
    RCTLogInfo(@"%@", name);
    RCTLogInfo(@"%@", ccv);
    RCTLogInfo(@"%@", month);
    RCTLogInfo(@"%@", year);

    NSString *events = [rn generateHashWithCardNumber:number holderName:name securityCode:ccv expirationMonth:month expirationYear:year];
    
    callback(@[[NSNull null], events]);
    
}   

 @end
