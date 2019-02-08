import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Persona } from '.'

const app = () => express(apiRoot, routes)

let persona

beforeEach(async () => {
  persona = await Persona.create({})
})

test('POST /personas 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ nombre: 'test', fecha_nacimiento: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.nombre).toEqual('test')
  expect(body.fecha_nacimiento).toEqual('test')
})

test('GET /personas 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /personas/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${persona.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(persona.id)
})

test('GET /personas/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /personas/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${persona.id}`)
    .send({ nombre: 'test', fecha_nacimiento: 'test', genero: 'test', enfermedad: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(persona.id)
  expect(body.nombre).toEqual('test')
  expect(body.fecha_nacimiento).toEqual('test')
  expect(body.genero).toEqual('test')
  expect(body.enfermedad).toEqual('test')
})

test('PUT /personas/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ nombre: 'test', fecha_nacimiento: 'test', genero: 'test', enfermedad: 'test' })
  expect(status).toBe(404)
})

test('DELETE /personas/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${persona.id}`)
  expect(status).toBe(204)
})

test('DELETE /personas/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
