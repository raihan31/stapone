'use strict';

describe('Controller Tests', function() {

    describe('UserReviews Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockUserReviews, MockUser, MockProducts;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockUserReviews = jasmine.createSpy('MockUserReviews');
            MockUser = jasmine.createSpy('MockUser');
            MockProducts = jasmine.createSpy('MockProducts');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'UserReviews': MockUserReviews,
                'User': MockUser,
                'Products': MockProducts
            };
            createController = function() {
                $injector.get('$controller')("UserReviewsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'staponeApp:userReviewsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
