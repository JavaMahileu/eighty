'use strict';

describe("deleteQuestionInstanceCtrl", function () {

    var scope, ctrl;
    beforeEach(module('eightyControllers'));

    beforeEach(inject(function ($rootScope, $controller) {
        scope = $rootScope.$new();

        scope = {
                $close: function (result) {
                },
                $dismiss: function () {
                }
            };
        ctrl = $controller("deleteQuestionInstanceCtrl", {$scope: scope});
        spyOn(scope, "$close");
        spyOn(scope, "$dismiss");
    }));

    it("cancel delete question click", function () {
        ctrl.cancelDeleteQuestion();
        expect(scope.$dismiss).toHaveBeenCalled();
    });

    it("delete question click", function () {
        ctrl.confirmDeleteQuestion();
        expect(scope.$close).toHaveBeenCalled();
    });
});

