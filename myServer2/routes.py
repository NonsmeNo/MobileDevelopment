import sqlalchemy
from flask import Blueprint, request, jsonify
from models import db, GasStations, Cars

api_bp = Blueprint('api', __name__)


@api_bp.route('/', methods=['GET'])
def main():
    code = request.args.get('code')
    if code == '10':
        data = GasStations.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    elif code == '20':
        data = Cars.query.all()
        item_list = [item.to_dict() for item in data]
        return jsonify({"items": item_list}), 200
    return jsonify({'error': 'Invalid code'}), 400


@api_bp.route('/gasstations', methods=['POST'])
def manage_gasstations():
    data = request.get_json()
    gasst = data['gasstations']

    if data['action'] == 11: #добавление заправки
        new_gas_station = GasStations(id=gasst['id'], gas_station_name=gasst['name'])
        db.session.add(new_gas_station)
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 12: #изменение заправки
        gas_station = GasStations.query.get_or_404(gasst['id'])
        gas_station.gas_station_name = gasst['name']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 13: #удаление заправки
        gas_station = GasStations.query.get_or_404(gasst['id'])
        db.session.delete(gas_station)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400




@api_bp.route('/cars', methods=['POST'])
def manage_scars():
    data = request.get_json()
    it = data['cars']
    print(data['action'])
    if data['action'] == 21:
        cars = Cars(
            id=it['id'],
            model_car=it['model'],
            number_car=it['num'],
            brand_car=it['brand'],
            color_car=it['color'],
            fio_owner=it['owner'],
            type_gas=it['gas'],
            volume_gas=it['volume'],
            date_gas=it['date'],
            gas_station_id=it['gasStationID']
        )
        db.session.add(cars)
        db.session.commit()
        return jsonify({}), 200

    elif data['action'] == 22:
        cars = Cars.query.get_or_404(it['id'])
        cars.model_car = it['model']
        cars.number_car = it['num']
        cars.brand_car = it['brand']
        cars.color_car = it['color']
        cars.fio_owner = it['owner']
        cars.type_gas = it['gas']
        cars.volume_gas = it['volume']
        cars.date_gas = it['date']
        cars.gas_station_id = it['gasStationID']
        db.session.commit()
        return jsonify({}), 200
    
    elif data['action'] == 23:
        car = Cars.query.get_or_404(it['id'])
        db.session.delete(car)
        db.session.commit()
        return jsonify({}), 200

    return jsonify({'error': 'Invalid action'}), 400