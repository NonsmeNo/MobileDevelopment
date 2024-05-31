from flask_sqlalchemy import SQLAlchemy
from uuid import uuid4

db = SQLAlchemy()

class GasStations(db.Model):
    __tablename__ = 'gasstations'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    gas_station_name = db.Column(db.String(128), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'name': self.gas_station_name
        }
class Cars(db.Model):
    __tablename__ = 'cars'
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid4()))
    model_car = db.Column(db.String(128), nullable=False)
    number_car = db.Column(db.String(128), nullable=False)
    brand_car = db.Column(db.String(128), nullable=False)
    color_car = db.Column(db.String(128), nullable=False)
    fio_owner = db.Column(db.String(128), nullable=False)
    type_gas = db.Column(db.Integer, nullable=False)
    volume_gas = db.Column(db.String(128), nullable=False)
    date_gas = db.Column(db.String(10), nullable=False)
    gas_station_id = db.Column(db.String(36), db.ForeignKey('gasstations.id'), nullable=False)

    def to_dict(self):
        return {
            'id': self.id,
            'model': self.model_car,
            'num': self.number_car,
            'brand': self.brand_car,
            'color': self.color_car,
            'owner': self.fio_owner,
            'gas': self.type_gas,
            'volume': self.volume_gas,
            'date': self.date_gas,
            'gasStationID': self.gas_station_id
        }