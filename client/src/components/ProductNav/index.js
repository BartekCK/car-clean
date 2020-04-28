import React from 'react';
import { Button, Nav, Navbar } from 'react-bootstrap';

export class ProductNav extends React.Component {
  state = {
    categories: []
  };

  componentDidMount = () => {
    fetch('http://localhost:8080/api/v1/product/categories')
        .then(res => res.json())
        .then(categories => {
              console.log(categories);
              this.setState({categories});
            }
        );
  };

  render() {
    const { setProductCategory, activeCategory } = this.props;
    const { categories } = this.state;

    return (
      <Navbar bg='light' expand='md' className='border-bottom '>
        <Navbar.Toggle />
        <Navbar.Collapse>
          <Nav className='mr-auto '>
            {categories &&
              categories.map((category, id) => (
                <Button
                  key={id}
                  className='m-1'
                  variant='outline-dark'
                  value={category}
                  onClick={(e) => setProductCategory(e.target.value)}
                >
                  {category}
                </Button>
              ))}
            {activeCategory && (
              <Button
                className='m-1'
                onClick={() => setProductCategory('')}
                variant='outline-danger'
              >
                Wyczyść filtry
              </Button>
            )}
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  }
}
