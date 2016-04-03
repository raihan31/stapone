'use strict';

describe('Controller Tests', function() {

    describe('DeptPromotionPhoto Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeptPromotionPhoto, MockDepartment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeptPromotionPhoto = jasmine.createSpy('MockDeptPromotionPhoto');
            MockDepartment = jasmine.createSpy('MockDepartment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DeptPromotionPhoto': MockDeptPromotionPhoto,
                'Department': MockDepartment
            };
            createController = function() {
                $injector.get('$controller')("DeptPromotionPhotoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'staponeApp:deptPromotionPhotoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
