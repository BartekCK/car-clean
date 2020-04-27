import React from 'react';
import { Container, Row } from 'react-bootstrap';
import { OfferDiv } from '../../components/Offer';
//import { offers } from '../../data/temp/OffersTemp';
import axios from 'axios'

export class Offer extends React.Component {
  state = {
    offers: []
  };

    componentDidMount = () => {
        axios.get('http://localhost:8080/api/v1/services')
            .then(response => {
                this.setState({offers: response.data})
            })
            .catch(error => {
                console.log('error')
            })
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
