import React from 'react';
import { Badge, Col, Container, Row } from 'react-bootstrap';
import { offers } from '../../data/temp/OffersTemp';
import { OfferDiv } from '../../components/Offer';
import { CarTable } from '../../components/CarTable';

export class ChosenOffer extends React.Component {
  state = {
    offer: null,
    currentCar: null,
  };

  componentDidMount = () => {
    //POST TO CHECK USER IF WRONG THEN REDIRECT !!!
    //GET FROM API SERVICE WITH ID IN URL
    const offer = offers.find(
      (offer) => offer.id === Number(this.props.match.params.id)
    );
    console.log(offer);
    this.setState({ offer: offer });
  };

  setUserCar = (id) => {
    //GET FROM API THIS CAR WHERE OWNER IS USER BY ID USERID=> CARS === CARID
    const temp = {
      id: 0,
      brand: 'Mercedes',
      plates_number: 'THI66666',
    };
    this.setState({ currentCar: temp });
  };

  render() {
    const { offer, currentCar } = this.state;
    return (
      <Container className='p-2'>
        {offer && (
          <Row className='flex-column shadow my-3'>
            <Col>
              <h1>Wybrana oferta</h1>
            </Col>
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
                onClick={() => this.setState({ currentCar: null })}
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
          <Row className='flex-column shadow my-3 p-2'>
            <h1>Wybierz datę zabiegu</h1>
          </Row>
        )}
      </Container>
    );
  }
}
