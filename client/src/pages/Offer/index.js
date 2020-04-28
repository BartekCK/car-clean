import React from 'react';
import { Container, Row } from 'react-bootstrap';
import { OfferDiv } from '../../components/Offer';
import { getAllServices } from '../../helpers/apiCommands';
import { ErrorDiv } from '../../helpers/error';

export class Offer extends React.Component {
  state = {
    offers: [],
    error: null,
  };

  componentDidMount = async () => {
    try {
      const result = await getAllServices();
      this.setState({ offers: result });
    } catch (e) {
      const error = { message: 'Brak dostępnych ofert' };
      this.setState({ error: error });
    }
  };

  render() {
    const { offers, error } = this.state;
    if (error) {
      return <ErrorDiv message={error.message} />;
    } else
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
