import React from 'react';
import {Badge, Button, Col, Container, ListGroup, Row} from 'react-bootstrap';
//import {offers} from '../../data/temp/OffersTemp';
import {OfferDiv} from '../../components/Offer';
import {CarTable} from '../../components/CarTable';
import {Calendar} from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import {Redirect} from 'react-router-dom';
import {ErrorModal} from '../../helpers/error';
import axios from "axios";

export class ChosenOffer extends React.Component {
  state = {
    offer: null,
    currentCar: null,
    chosenDate: new Date(),
    freeHours: [],
    chosenHour: null,
    isGood: null,
  };

  componentDidMount = () => {
    //POST TO CHECK USER IF WRONG THEN REDIRECT !!!
    //GET FROM API SERVICE WITH ID IN URL
    axios.get(`http://localhost:8080/api/v1/services/${this.props.match.params.id}`)
        .then(response => {
          this.setState({offer: response.data})
        })
        .catch(error => {
          console.log('error')
        })
  };

  setUserCar = (id) => {
    //THIS ID IS CONNECTED WITH COMPOMENT CARTABLE SO EASY !!!
    //GET FROM API THIS CAR WHERE OWNER IS USER BY ID USERID=> CARS === CARID
    const temp = {
      id: 0,
      brand: 'Mercedes',
      plates_number: 'THI66666',
    };
    this.setState({ currentCar: temp });
  };

  changeDate = (chosenDate) => {
    //POST TO API THIS DATE AND GET FREE HOURS THIS DAY
    this.setState({
      chosenDate: chosenDate,
      freeHours: ['10:00', '11:00'],
      chosenHour: null,
    });
  };

  setHour = (e) => {
    this.setState({ chosenHour: e.target.value });
  };

  saveAllData = () => {
    //POST TO THE SERVER USERID CARID DATE AND OFFER ID
    //IF GOOD THEN REDIRECT ELSE SHOW MODAL WITH ERROR
    this.setState({ isGood: false });
  };

  render() {
    const {
      offer,
      currentCar,
      chosenDate,
      freeHours,
      chosenHour,
      isGood,
    } = this.state;
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
            />
          </Col>
          {currentCar && (
            <Col>
              <h4>Wybrany pojazd</h4>
              <Badge pill={true} variant='primary'>
                Model: {currentCar.brand}
              </Badge>
              <Badge pill={true} variant='primary'>
                Model: {currentCar.plates_number}
              </Badge>
              <Badge
                onClick={() =>
                  this.setState({
                    currentCar: null,
                    freeHours: [],
                    chosenHour: null,
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

        {currentCar && (
          <Row xs={1} sm={2} className='shadow my-3 p-2'>
            <Col>
              <Calendar onChange={this.changeDate} value={chosenDate} />
            </Col>
            <Col>
              <Badge variant='primary'>
                Wolne terminy dnia {chosenDate.getDate()}.
                {chosenDate.getMonth()}.{chosenDate.getFullYear()}
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
                      {hour}
                    </ListGroup.Item>
                  ))}
              </ListGroup>
            </Col>
          </Row>
        )}

        {/*SHOW CONFIRM DATA*/}
        {chosenHour && (
          <Row className='shadow my-3 p-2 justify-content-between'>
            <div>
              <h5>Twój termin: </h5>
              <Badge pill={true} variant='primary'>
                Dzień: {chosenDate.getDate()}.{chosenDate.getMonth()}.
                {chosenDate.getFullYear()}
              </Badge>
              <Badge pill={true} variant='primary'>
                Godzina: {chosenHour}:00
              </Badge>
            </div>
            <Button onClick={this.saveAllData} variant='outline-success'>
              Potwierdzam
            </Button>
          </Row>
        )}
        {isGood && <Redirect to='/' />}
        {isGood === false && (
          <ErrorModal onHide={() => this.setState({ isGood: null })} />
        )}
      </Container>
    );
  }
}
