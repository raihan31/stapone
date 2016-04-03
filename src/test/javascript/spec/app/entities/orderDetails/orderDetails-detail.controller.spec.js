'use strict';

describe('Controller Tests', function() {

    describe('OrderDetails Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockOrderDetails, MockOrders, MockProducts;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockOrderDetails = jasmine.createSpy('MockOrderDetails');
            MockOrders = jasmine.createSpy('MockOrders');
            MockProducts = jasmine.createSpy('MockProducts');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'OrderDetails': MockOrderDetails,
                'Orders': MockOrders,
                'Products': MockProducts
            };
            createController = function() {
                $injector.get('$controller')("OrderDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'staponeApp:orderDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
