'use strict';

describe("editTopicInstanceController", function () {

    var scope, controller, node;

    beforeEach(module('eightyFactories'));
    beforeEach(module('eightyControllers'));

    beforeEach(inject(function ($controller) {

        scope = {
            $close: function (result) {
            },
            $dismiss: function () {
            }
        };

        spyOn(scope, "$close");
        spyOn(scope, "$dismiss");
        node = {"title": "fakeTopic", "data": {"id": 3}}

        controller = $controller("editTopicInstanceCtrl", {
            $scope: scope,
            node: node
        });

    }));

    it("cancel edit topic click", function () {
        controller.cancelEditTopic();
        expect(scope.$dismiss).toHaveBeenCalled();
    });

    it("edit topic click when a title is not null", function () {
        controller.topic.title = 'fake topic';
        controller.saveTopicChanges();
        expect(scope.$close).toHaveBeenCalledWith(controller.topic);
    });

    it("edit topic click when a title is null", function () {
        controller.topic.title = null;
        controller.saveTopicChanges();
        expect(scope.$close).not.toHaveBeenCalled();
    });
});