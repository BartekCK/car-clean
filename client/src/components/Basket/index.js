import Payments24 from '../../resources/img/add/przelewy24.png';
import MastercardImage from '../../resources/img/add/mastercard.png';
import VisaImage from '../../resources/img/add/visa.png';
import { Accordion, Button, Card, FormControl, InputGroup } from 'react-bootstrap';
import React from 'react';
import styled from 'styled-components';
import { createUserOrderProducts } from '../../helpers/apiCommands';

export const AddDiv = () => (
  <AddContainer>
    <AddImage alt='' src={Payments24} />
    <AddImage alt='' src={MastercardImage} />
    <AddImage alt='' src={VisaImage} />
  </AddContainer>
);

export const SingleInputProduct = ({ id, name, price, deleteProd }) => (
  <InputGroup className='my-1'>
    <FormControl disabled={true} placeholder={name + ' ' + price + ' zł'} />
    <Button onClick={() => deleteProd(id)} variant='danger'>
      -
    </Button>
  </InputGroup>
);

const basketToOrder = async () => {
    // ADD ORDER TO API FROM BASKET
    try {
        const result = await createUserOrderProducts();
        console.log(result)
    } catch (e) {
        const error = { message: 'Błąd przy tworzeniu zamówienia' };
        this.setState({ error: error });
    }
};

export const PaymentButton = () => (
    <Button onClick={basketToOrder} className='mr-1 align-self-stretch' variant='primary'>
        Zatwierdź koszyk
    </Button>
);

export const AddImage = styled.img`
  max-width: 170px;
  max-height: 90px;
`;

const AddContainer = styled.div`
  display: inline-block;
  text-align: center;
`;
