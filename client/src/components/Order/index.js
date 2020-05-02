import { Button } from 'react-bootstrap';
import React from 'react';

export const SingleInputOrder = ({ id, prods, bill, status, changeStatus }) => (
    <tr>
        <th>{id}</th>
        <th>{prods}</th>
        <th>{bill}</th>
        <th>{status}
        <Button onClick={() => changeStatus(id)} variant='danger'>
            Opłać
        </Button>
        </th>
    </tr>
);

