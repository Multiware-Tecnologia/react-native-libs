//
//  GNApiClient.m
//  GNApiSdk
//
//  Created by Thomaz Feitoza on 5/5/15.
//  Copyright (c) 2015 Gerencianet. All rights reserved.
//

#import "GNApiClient.h"


@implementation GNApiClient

NSString *const kGNApiBaseUrlProduction = @"https://api.gerencianet.com.br/v1";
NSString *const kGNApiBaseUrlSandbox = @"https://sandbox.gerencianet.com.br/v1";

- (instancetype)initWithConfig:(GNConfig *)config {
    self = [super init];
    _config = config;
    return self;
}

- (PMKPromise *) request:(NSString *)route method:(NSString *)method params:(NSDictionary *)params {
    if(!_config.accountCode){
        [NSException raise:@"Account code not defined" format:@"Please setup your GN account code before making requests"];
    }
    
    NSString *url = [NSString stringWithFormat:@"%@%@", (_config.sandbox ? kGNApiBaseUrlSandbox : kGNApiBaseUrlProduction), route];
    AFHTTPSessionManager *Manager = [AFHTTPSessionManager manager];
    [Manager.requestSerializer setValue:_config.accountCode forHTTPHeaderField:@"account-code"];
    
    return [PMKPromise new:^(PMKFulfiller fulfill, PMKRejecter reject) {
        
        id successBlock = ^(AFHTTPSessionManager *operation, id responseObject) {
            NSError *err = nil;
            NSDictionary *responseDict = [NSJSONSerialization JSONObjectWithData:operation options:NSJSONReadingMutableContainers error:&err];
            GNError *gnApiErr = nil;
            if(err){
                gnApiErr = [[GNError alloc] initWithCode:500 message:@"Invalid response data."];
                reject(gnApiErr);
            }
            else if([responseDict objectForKey:@"error"]){
                gnApiErr = [[GNError alloc] initWithDictionary:responseDict];
                reject(gnApiErr);
            }
            else {
                fulfill(responseDict);
            }
        };
        
        id failureBlock = ^(NSURLSessionTask *operation, NSError *error) {
            NSError *err;
            NSDictionary *responseDict;
            GNError *gnApiErr;
            if(operation.response){
                responseDict = [NSJSONSerialization JSONObjectWithData:operation.response options:NSJSONReadingMutableContainers error:&err];
            }
            if(!err && responseDict){
                gnApiErr = [[GNError alloc] initWithDictionary:responseDict];
            } else {
                gnApiErr = [[GNError alloc] initWithCode:500 message:@"Invalid response data."];
            }
            
            reject(gnApiErr);
        };
        
        
        if ([method isEqualToString:@"POST"]) {
            [[AFJSONRequestSerializer serializer] requestWithMethod:@"POST" URLString:url parameters:params error:nil];
        } else if ([method isEqualToString:@"GET"]) {
            [[AFHTTPRequestSerializer serializer] requestWithMethod:@"GET" URLString:url parameters:params error:nil];
        }
    }];
}

@end
