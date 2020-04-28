import React from 'react';
import { Button, Container, Table } from 'react-bootstrap';

export const CarTable = ({ userId, actionStart, actionTitle, cars }) => {
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
              <tr key={car.id}>
                <td>{car.brand}</td>
                <td>{car.platesNumber}</td>
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
