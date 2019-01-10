import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Tratamiento } from '.'

const app = () => express(apiRoot, routes)

let tratamiento

beforeEach(async () => {
  tratamiento = await Tratamiento.create({})
})

test('POST /tratamientos 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ periodo_toma: 'test', duracion: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.periodo_toma).toEqual('test')
  expect(body.duracion).toEqual('test')
})

test('GET /tratamientos 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /tratamientos/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${tratamiento.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(tratamiento.id)
})

test('GET /tratamientos/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /tratamientos/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${tratamiento.id}`)
    .send({ periodo_toma: 'test', duracion: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(tratamiento.id)
  expect(body.periodo_toma).toEqual('test')
  expect(body.duracion).toEqual('test')
})

test('PUT /tratamientos/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ periodo_toma: 'test', duracion: 'test' })
  expect(status).toBe(404)
})

test('DELETE /tratamientos/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${tratamiento.id}`)
  expect(status).toBe(204)
})

test('DELETE /tratamientos/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
