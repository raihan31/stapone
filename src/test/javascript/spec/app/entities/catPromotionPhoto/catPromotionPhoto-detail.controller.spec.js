'use strict';

describe('Controller Tests', function() {

    describe('CatPromotionPhoto Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCatPromotionPhoto, MockCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCatPromotionPhoto = jasmine.createSpy('MockCatPromotionPhoto');
            MockCategory = jasmine.createSpy('MockCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'CatPromotionPhoto': MockCatPromotionPhoto,
                'Category': MockCategory
            };
            createController = function() {
                $injector.get('$controller')("CatPromotionPhotoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'staponeApp:catPromotionPhotoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
