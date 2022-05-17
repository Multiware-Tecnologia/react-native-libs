#import "GNApiSdk.h"
#import "RCTLog.h"

@implementation RNGNT

RCT_EXPORT_MODULE()

// Example method
// See // https://reactnative.dev/docs/native-modules-ios

  RCT_EXPORT_METHOD(generateHash: (NSString *)number :(NSString *)brand :(NSString *)ccv :(NSString *)month :(NSString *)year :(NSString *)accountId :(Bool *)approvalMode :(RCTResponseSenderBlock)callback)
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
    })
    .catch(^(GNError *error){
    NSLog(@"An error occurred: %@", error.message);
    });
}




  NSNumber *result = @([a floatValue] * [b floatValue]);

  resolve(result);
}

@end
