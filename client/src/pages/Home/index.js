import React from 'react';
import {Button, Carousel, Col, Container, Image, Row} from 'react-bootstrap';
import CarPartsImage from '../../resources/img/add/car-parts.jpg';
import CarWashImage from '../../resources/img/add/car-wash.png';
import styled from 'styled-components';
import Mechanic_1_Image from '../../resources/img/home/mechanic_1.jpg';
import Mechanic_2_Image from '../../resources/img/home/mechanic_2.jpg';

export class Home extends React.Component {
  render() {
    return (
      <>
        <Carousel className='mb-4'>
          <Carousel.Item>
            <Img className='d-block w-100' src={CarPartsImage} alt='sklep' />
            <Carousel.Caption>
              <h3>Najlepsze środki czystości</h3>
              <Button
                onClick={() => this.props.history.push(`/sklep`)}
                variant='primary'
              >
                Kup teraz
              </Button>
            </Carousel.Caption>
          </Carousel.Item>
          <Carousel.Item>
            <Img className='d-block w-100' src={CarWashImage} alt='oferta' />
            <Carousel.Caption>
              <h3>Pielęgnacja pojazdu</h3>
              <Button
                onClick={() => this.props.history.push(`/oferta`)}
                variant='primary'
              >
                Sprawdź sam
              </Button>
            </Carousel.Caption>
          </Carousel.Item>
        </Carousel>

        <Container className='my-2 p-2'>
          <h1>O nas</h1>
          <hr className='border-dark' />
          <P>
            Lorem Ipsum is simply dummy text of the printing and typesetting
            industry. Lorem Ipsum has been the industry's standard dummy text
            ever since the 1500s, when an unknown printer took a galley of type
            and scrambled it to make a type specimen book. It has survived not
            only five centuries, but also the leap into electronic typesetting,
            remaining essentially unchanged. It was popularised in the 1960s
            with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus
            PageMaker including versions of Lorem Ipsum.
          </P>
        </Container>
        <Container className='my-2 p-2'>
          <h1>Dlaczego my</h1>
          <hr className='border-dark' />
          <P>
            Lorem Ipsum is simply dummy text of the printing and typesetting
            industry. Lorem Ipsum has been the industry's standard dummy text
            ever since the 1500s, when an unknown printer took a galley of type
            and scrambled it to make a type specimen book. It has survived not
            only five centuries, but also the leap into electronic typesetting,
            remaining essentially unchanged. It was popularised in the 1960s
            with the release of Letraset sheets containing Lorem Ipsum passages,
            and more recently with desktop publishing software like Aldus
            PageMaker including versions of Lorem Ipsum.
          </P>
        </Container>
        <Container className='my-2 p-2'>
          <h1>Poznaj nas</h1>
          <hr className='border-dark' />
          <Row xs={1} md={2} className='my-3'>
            <Col>
              <Image src={Mechanic_1_Image} thumbnail />
            </Col>
            <Col>
              <P>
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the industry's standard dummy
                text ever since the 1500s, when an unknown printer took a galley
                of type and scrambled it to make a type specimen book. It has
                survived not only five centuries, but also the leap into
                electronic typesetting, remaining essentially unchanged. It was
                popularised in the 1960s with the release of Letraset sheets
                containing Lorem Ipsum passages, and more recently with desktop
                publishing software like Aldus PageMaker including versions of
                Lorem Ipsum.
              </P>
            </Col>
          </Row>
          <Row xs={1} md={2}>
            <Col xs={{ order: 2 }} md={{ order: 1 }}>
              <P>
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the industry's standard dummy
                text ever since the 1500s, when an unknown printer took a galley
                of type and scrambled it to make a type specimen book. It has
                survived not only five centuries, but also the leap into
                electronic typesetting, remaining essentially unchanged. It was
                popularised in the 1960s with the release of Letraset sheets
                containing Lorem Ipsum passages, and more recently with desktop
                publishing software like Aldus PageMaker including versions of
                Lorem Ipsum.
              </P>
            </Col>
            <Col xs={{ order: 1 }}>
              <Image src={Mechanic_2_Image} thumbnail />
            </Col>
          </Row>
        </Container>
      </>
    );
  }
}

const Img = styled.img`
  max-width: 100vw;
  max-height: 92vh;
`;

const P = styled.p`
  color: #444444;
`;
