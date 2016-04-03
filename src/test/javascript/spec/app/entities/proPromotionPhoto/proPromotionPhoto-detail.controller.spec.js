'use strict';

describe('Controller Tests', function() {

    describe('ProPromotionPhoto Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProPromotionPhoto, MockProducts;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProPromotionPhoto = jasmine.createSpy('MockProPromotionPhoto');
            MockProducts = jasmine.createSpy('MockProducts');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ProPromotionPhoto': MockProPromotionPhoto,
                'Products': MockProducts
            };
            createController = function() {
                $injector.get('$controller')("ProPromotionPhotoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'staponeApp:proPromotionPhotoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
