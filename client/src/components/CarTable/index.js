import React, { useEffect, useState } from 'react';
import { Button, Container, Table } from 'react-bootstrap';

export const CarTable = ({ userId, actionStart, actionTitle }) => {
  const [cars, setCars] = useState([]);

  useEffect(() => {
    //GET ALL USER CAR BY USER ID
    //USER ID MUST BE IN props
    const carsTemp = [
      // TEMP
      {
        id: 0,
        brand: 'Mercedes',
        plates_number: 'THI66666',
      },
    ];
    setCars(carsTemp);
  }, []);

  return (
    <Container>
      {cars.length > 0 ? (
        <Table striped bordered hover size='sm' className='text-center'>
          <thead>
            <tr>
              <th>Model</th>
              <th>Numer rejestracyjny</th>
              <th>Akcja</th>
            </tr>
          </thead>
          <tbody>
            {cars.map((car) => (
              <tr>
                <td>{car.brand}</td>
                <td>{car.plates_number}</td>
                <td>
                  <Button onClick={() => actionStart(car.id)}>
                    {actionTitle}
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      ) : (
        <h3>Brak pojazdów użytkownika</h3>
      )}
    </Container>
  );
};