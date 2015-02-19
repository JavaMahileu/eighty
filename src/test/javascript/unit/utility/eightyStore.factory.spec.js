'use strict';

describe('service', function () {

    beforeEach(module('eightyApp'));

    it('check the existence of eightyStore', inject(function (eightyStore) {
        expect(eightyStore).toBeDefined();
    }));
    describe('eightyStore', function () {
        var keyObject, object, keyArr, arr;
        var factory;

        beforeEach(function() {
            keyObject = 'keyObject';
            keyArr = 'keyArr';
            object = {id: 0, string: 'string', number: 100};
            arr = [object, object, object];
        });

        beforeEach(inject(function(eightyStore) {
            factory = eightyStore;
        }));

        it('set', function() {
            spyOn(factory, 'set');
            factory.set(keyObject, object);
            expect(factory.set).toHaveBeenCalled();
            expect(factory.set).toHaveBeenCalledWith(keyObject, object);
            factory.set(keyArr, arr);
            expect(factory.set).toHaveBeenCalled();
            expect(factory.set).toHaveBeenCalledWith(keyArr, arr);
        });

        it('get', function() {
            factory.set(keyObject, object);
            expect(factory.get(keyObject)).toEqual(object);
            factory.set(keyArr, arr);
            expect(factory.get(keyArr)).toEqual(arr);
            factory.remove(keyObject);
            factory.remove(keyArr);
        });

        it('remove', function() {
            factory.set(keyObject, object);
            factory.set(keyArr, arr);
            expect(factory.get(keyObject)).not.toBeNull();
            expect(factory.get(keyArr)).not.toBeNull();
            factory.remove(keyObject);
            factory.remove(keyArr);
            expect(factory.get(keyObject)).toBeNull();
            expect(factory.get(keyArr)).toBeNull();
        });
    });
})