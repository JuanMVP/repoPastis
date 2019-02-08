import { Persona } from '.'

let persona

beforeEach(async () => {
  persona = await Persona.create({ nombre: 'test', fecha_nacimiento: 'test', genero: 'test', enfermedad: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = persona.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(persona.id)
    expect(view.nombre).toBe(persona.nombre)
    expect(view.fecha_nacimiento).toBe(persona.fecha_nacimiento)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = persona.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(persona.id)
    expect(view.nombre).toBe(persona.nombre)
    expect(view.fecha_nacimiento).toBe(persona.fecha_nacimiento)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
