import React from 'react';
import { Col, Container, Row } from 'react-bootstrap';

import { OfferDiv } from '../../components/Offer';
import { offers } from '../../data/temp/OffersTemp';

export class Offer extends React.Component {
  state = {
    offers: null,
  };

  componentDidMount = () => {
    this.setState({ offers: offers });
  };

  render() {
    const { offers } = this.state;
    return (
      <Container className='mt-1'>
        <Row md={1} lg={2} xl={3}>
          {offers &&
            offers.map((offer) => (
              <Col>
                <OfferDiv
                  title={offer.name}
                  photo={offer.image}
                  description={offer.description}
                  price={offer.price}
                />
              </Col>
            ))}
        </Row>
      </Container>
    );
  }
}
