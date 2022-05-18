#import "GNApiSdk.h"
#import "RCTLog.h"
#import "GNCreditCard.h"
#import "GNPaymentToken.h"
#import "GNConfig.h"
#import "GNError.h"
#import "GNApiEndpoints.h"

@implementation RNGNT

RCT_EXPORT_MODULE()

// Example method
// See // https://reactnative.dev/docs/native-modules-ios

  RCT_EXPORT_METHOD(generateHash: (NSString *)number :(NSString *)brand :(NSString *)ccv :(NSString *)month :(NSString *)year :(NSString *)accountId :(Boolean *)approvalMode :(RCTResponseSenderBlock)callback)
{
  GNConfig *gnConfig = [[GNConfig alloc] initWithAccountCode:accountId sandbox:approvalMode];
  GNApiEndpoints *gnApi = [[GNApiEndpoints alloc] initWithConfig:gnConfig];

  GNCreditCard *creditCard = [[GNCreditCard alloc] init];
      creditCard.number = number;
      creditCard.brand = brand;
      creditCard.expirationMonth = month;
      creditCard.expirationYear = year;
      creditCard.cvv = ccv;

    [gnApi paymentTokenForCreditCard:creditCard]
    .then(^(GNPaymentToken *paymentToken){
    NSLog(@"%@", paymentToken.token);
    // NSString *events =[rn generateHashWithCardNumber:number cardBrand:brand securityCode:ccv expirationMonth:expirationMonth expirationYear:expirationYear ]
    callback(@[[NSNull null], paymentToken.token]);
    })
    .catch(^(GNError *error){
    NSLog(@"An error occurred: %@", error.message);
    callback(@[[NSNull null], error.message]);
    });
}

@end
