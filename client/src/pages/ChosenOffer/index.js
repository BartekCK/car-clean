import React from 'react';
import { Badge, Button, Col, Container, ListGroup, Row } from 'react-bootstrap';
//import {offers} from '../../data/temp/OffersTemp';
import { OfferDiv } from '../../components/Offer';
import { CarTable } from '../../components/CarTable';
import { Calendar } from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { Redirect } from 'react-router-dom';
import { ErrorDiv, ErrorModal } from '../../helpers/error';
import {
  addReservationServices,
  getAllUserCars,
  getFreeHoursServices,
  getServiceById,
  getUserCars,
} from '../../helpers/apiCommands';
import { formatDate } from '../../helpers/time';

export class ChosenOffer extends React.Component {
  state = {
    offer: null,
    carId: null,
    date: new Date(),
    freeHours: [],
    time: null,
    cars: [],
    isGood: null,
    error: '',
    errorApiMessage: '',
  };

  componentDidMount = async () => {
    //POST TO CHECK USER IF WRONG THEN REDIRECT !!!
    //GET FROM API SERVICE WITH ID IN URL
    try {
      const result = await getServiceById(this.props.match.params.id);
      const cars = await getAllUserCars();
      this.setState({ cars: cars.data, showModal: true, offer: result });
    } catch (e) {
      if (e.response) this.setState({ error: e.response.data });
      else this.setState({ error: 'Brak oferty' });
    }
  };

  setUserCar = async (id) => {
    try {
      const res = await getUserCars(id);
      this.setState({ carId: res.data });
    } catch (e) {
      console.log(e);
    }
  };

  changeDate = async (chosenDate) => {
    const temp = new Date(Date.now()).setDate(
      new Date(Date.now()).getDate() - 1
    );
    if (new Date(chosenDate) >= temp) {
      try {
        const result = await getFreeHoursServices(
          new Date(formatDate(chosenDate))
        );
        console.log(result);
        this.setState({
          date: chosenDate,
          freeHours: result.data,
          time: null,
        });
      } catch (e) {
        console.log(e);
      }
    } else {
      this.setState({
        isGood: false,
        errorApiMessage: 'Termin został niepoprawnie wybrany',
      });
    }
  };

  setHour = (e) => {
    this.setState({ time: e.target.value });
  };

  saveAllData = async () => {
    try {
      await addReservationServices({
        ...this.state,
        date: formatDate(this.state.date),
        carId: this.state.carId.id,
        servicesId: this.state.offer.id,
      });
      this.setState({ isGood: true });
    } catch (e) {
      console.log(e);
      if (e.response)
        this.setState({ isGood: false, errorApiMessage: e.response.data });
      else this.setState({ isGood: false, errorApiMessage: 'Wystąpił błąd' });
    }
  };

  render() {
    const {
      offer,
      carId,
      date,
      freeHours,
      time,
      cars,
      isGood,
      error,
    } = this.state;
    if (error) return <ErrorDiv message={error} />;
    else
      return (
        <Container className='p-2'>
          {offer && (
            <Row className='flex-column shadow my-3'>
              <OfferDiv
                price={offer.price}
                id={offer.id}
                title={offer.name}
                photo={offer.image}
                description={offer.description}
                disable={true}
              />
            </Row>
          )}

          {/*CHOOSE USER CAR*/}
          <Row className='flex-column shadow my-3 p-2'>
            <Col>
              <CarTable
                userId={0}
                actionTitle={'Wybierz pojazd'}
                actionStart={this.setUserCar}
                cars={cars}
              />
            </Col>
            {carId && (
              <Col>
                <h4>Wybrany pojazd</h4>
                <Badge pill={true} variant='primary'>
                  Model: {carId.brand}
                </Badge>
                <Badge pill={true} variant='primary'>
                  Model: {carId.platesNumber}
                </Badge>
                <Badge
                  onClick={() =>
                    this.setState({
                      carId: null,
                      freeHours: [],
                      time: null,
                    })
                  }
                  pill={true}
                  variant='danger'
                >
                  X Usuń filtr
                </Badge>
              </Col>
            )}
          </Row>

          {/*CHOOSE SERVICE DATE*/}

          {carId && (
            <Row xs={1} sm={2} className='shadow my-3 p-2'>
              <Col>
                <Calendar onChange={this.changeDate} value={date} />
              </Col>
              <Col>
                <Badge variant='primary'>
                  Wolne terminy dnia {date.getDate()}.{date.getMonth() + 1}.
                  {date.getFullYear()}
                </Badge>
                <ListGroup as='ul' variant='flush'>
                  {freeHours.length > 0 &&
                    freeHours.map((hour, id) => (
                      <ListGroup.Item
                        action
                        as='li'
                        key={id}
                        value={hour}
                        onClick={this.setHour}
                      >
                        {hour}:00
                      </ListGroup.Item>
                    ))}
                </ListGroup>
              </Col>
            </Row>
          )}

          {/*SHOW CONFIRM DATA*/}
          {time && (
            <Row className='shadow my-3 p-2 justify-content-between'>
              <div>
                <h5>Twój termin: </h5>
                <Badge pill={true} variant='primary'>
                  Dzień: {date.getDate()}.{date.getMonth() + 1}.
                  {date.getFullYear()}
                </Badge>
                <Badge pill={true} variant='primary'>
                  Godzina: {time}:00
                </Badge>
              </div>
              <Button onClick={this.saveAllData} variant='outline-success'>
                Potwierdzam
              </Button>
            </Row>
          )}
          {isGood && <Redirect to='/' />}
          {isGood === false && (
            <ErrorModal
              text={this.state.errorApiMessage}
              onHide={() => this.setState({ isGood: null })}
            />
          )}
        </Container>
      );
  }
}
