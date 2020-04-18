import React from 'react';
import { Container, Row } from 'react-bootstrap';
import { OfferDiv } from '../../components/Offer';
import { offers } from '../../data/temp/OffersTemp';

export class Offer extends React.Component {
  state = {
    offers: null,
  };

  componentDidMount = () => {
    this.setState({ offers: offers }); //GET FROM API
  };

  render() {
    const { offers } = this.state;
    return (
      <Container className='mt-1'>
        <Row sm={1} md={2} xl={3}>
          {offers &&
            offers.map((offer) => (
              <OfferDiv
                key={offer.id}
                id={offer.id}
                title={offer.name}
                photo={offer.image}
                description={offer.description}
                price={offer.price}
              />
            ))}
        </Row>
      </Container>
    );
  }
}
