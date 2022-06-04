import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import App from '@/App';
import { InputRangeProvider } from '@/components/MultiRangeSlider/context/InputRange';
import { AccommodationProvider } from '@/contexts/Accommodation';
import '@/assets/style.css';
import theme from '@/styles/theme';

if (process.env.NODE_ENV === 'development') {
  // eslint-disable-next-line global-require
  const { worker } = require('@/mocks/browser');
  worker.start();
}

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <AccommodationProvider>
        <InputRangeProvider>
          <App />
        </InputRangeProvider>
      </AccommodationProvider>
    </BrowserRouter>
  </ThemeProvider>,
);
