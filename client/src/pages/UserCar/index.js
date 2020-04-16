import React from 'react';
import { Badge, Button, Col, Container, Modal, Row } from 'react-bootstrap';
import { CarTable } from '../../components/CarTable';
import { MyInput } from '../SignUp';
import { MdLocalCarWash, TiBusinessCard } from 'react-icons/all';
import styled from 'styled-components';
import CarImage from '../../resources/img/car/car-credentials.jpeg';
import { ErrorModal } from '../../helpers/error';

export class UserCar extends React.Component {
  state = {
    userId: 0,
    brand: '',
    plates_number: '',
    showModal: null,
  };

  componentDidMount = () => {
    //GET USER ID
  };

  deleteUserCar = (carID) => {
    //DELETE TO THE API CAR BY ID
    console.log(carID);
  };

  addUserCar = () => {
    const { userId, brand, plates_number } = this.state;
    if (brand.length > 0 && plates_number.length > 0) {
      //POST TO API WITH DATA LOCATED IN STATE
      console.log(userId);
      this.setState({ showModal: true }); //IF GOOD
      // this.setState({ showModal: false }); //IF WRONG
    }
  };

  updateCredentials = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  };

  render() {
    const { userId, brand, plates_number, showModal } = this.state;
    return (
      <Container>
        <Row className='d-flex flex-column'>
          <CarAddWrapper className='p-0'>
            <Col>
              <Badge variant='primary' className='my-2'>
                Dodawanie pojazdu
              </Badge>
            </Col>
            <MyInput
              name='brand'
              placeholder='Marka pojazdu'
              SpecialIcon={MdLocalCarWash}
              value={brand}
              updateCredentials={this.updateCredentials}
            />
            <MyInput
              name='plates_number'
              placeholder='Numery rejestracyjne'
              SpecialIcon={TiBusinessCard}
              value={plates_number}
              updateCredentials={this.updateCredentials}
            />
            <Col>
              <Button
                className='my-2 float-right'
                onClick={this.addUserCar}
                variant='success'
              >
                Dodaj pojazd
              </Button>
            </Col>
          </CarAddWrapper>
          <Col className='shadow my-2'>
            <Col className='my-2'>
              <Badge variant='primary'>Twoje pojazdy</Badge>
            </Col>
            <CarTable
              userId={userId}
              actionStart={this.deleteUserCar}
              actionTitle='Usuń pojazd'
            />
          </Col>
        </Row>
        {showModal === true && (
          <AddCarModal onHide={() => this.setState({ showModal: null })} />
        )}
        {showModal === false && (
          <ErrorModal onHide={() => this.setState({ showModal: null })} />
        )}
      </Container>
    );
  }
}

const CarAddWrapper = styled(Col)`
  background: url(${CarImage}) no-repeat;
  background-size: cover;
`;

export const AddCarModal = ({ onHide }) => (
  <Modal size='lg' centered show={true}>
    <Modal.Header>
      <p>Pojazd został dodany do bazy danych</p>
      <Button variant='success' onClick={onHide}>
        Dalej
      </Button>
    </Modal.Header>
  </Modal>
);
